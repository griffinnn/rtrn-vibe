package com.rtrn.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;

import com.rtrn.model.Item;
import com.rtrn.model.User;
import com.rtrn.repository.ItemRepository;
import com.rtrn.repository.UserRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

@Service
public class RtrnService {
    private final UserRepository userRepo = new UserRepository();
    private final ItemRepository itemRepo = new ItemRepository();

    public User register(String name, String email, boolean premium) {
        User user = new User(null, name, email, premium);
        return userRepo.save(user);
    }

    public Item addItem(User user, String name, String desc, String photoUrl, String contact) {
        int limit = user.isPremium() ? 10 : 3;
        if (user.getItems().size() >= limit) {
            throw new IllegalStateException("Item limit reached");
        }
        Item item = new Item(null, name, desc, photoUrl, contact);
        itemRepo.save(item);
        user.getItems().add(item);
        return item;
    }

    public List<Item> getUserItems(User user) {
        return user.getItems();
    }

    public Item getItem(Long id) {
        return itemRepo.findById(id);
    }

    public String generateQrCode(long itemId) throws WriterException, IOException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode("/item/" + itemId, BarcodeFormat.QR_CODE, 200, 200);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
        return Base64Utils.encodeToString(baos.toByteArray());
    }
}
