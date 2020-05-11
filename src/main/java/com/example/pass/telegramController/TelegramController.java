package com.example.pass.telegramController;
import com.example.pass.entity.TelegramUser;
//import com.example.pass.entity.Users;
import com.example.pass.repository.TelegramUsersRepository;
//import com.example.pass.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
@Component
@RestController
public class  TelegramController extends TelegramLongPollingBot {

//    @Autowired
//    UsersRepository usersRepository;

    @Autowired
    TelegramUsersRepository telegramUsersRepository;

    TelegramUser telegramUser = new TelegramUser();

    @Override
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());

        if (message.hasText()) {
            if (message.getText().equals("/start")) {
                if (!telegramUsersRepository.findByChatUserId(message.getChatId()).isPresent()) {
                    sendMessage.setChatId(message.getChatId());
                    sendMessage.setText("Hello! you have started to our courier" +
                            " bot if you want to get our messages, please share your contact number with under button!");
                    setButtons(sendMessage);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else if (message.getText().equals("/start")) {
                    System.out.println(message.getChatId());
                    sendMessage.setChatId(message.getChatId());
                    sendMessage.setText("You have already started!We know your number if we have messages we will send to you");
                    setButtonsEnd(sendMessage);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
                else if (message.getText().equals("/help")){
                    sendMessage.setChatId(message.getChatId());
                    sendMessage.setText("This bot for getting new from adminstrators!!! If we have news we will send to you!!!");
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText("You have sent wrong message! Please check it!");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (message.hasContact()) {
            if (telegramUsersRepository.findByPhoneNumber(message.getContact().getPhoneNumber()).isPresent()) {
                System.out.println("bor yoki yoq :" + telegramUsersRepository.findByPhoneNumber(message.getContact().getPhoneNumber()));
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("You have already added! If we have news we will send to you!!!");
            setButtonsEnd(sendMessage);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }else {
                sendMessage.setText("You have successfully added!!!  If we have messages we will send to you!!!");
                sendMessage.setChatId(message.getChatId());
                System.out.println("message wu : " + message);
                setButtonsEnd(sendMessage);
                telegramUsersRepository.save(new TelegramUser(message.getChatId(),message.getContact().getPhoneNumber()
                        .replace("+",""),message.getFrom().getFirstName(),message.getFrom().getUserName()));
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                System.out.println("message has contactni oldi : " + message.hasContact());
            }
        }else{
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("You have send wrong data!Please select true data!");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

        public void sendMessage(String message){
                SendMessage sendMessage = new SendMessage();
                List<TelegramUser> telegramUserList = telegramUsersRepository.findAll();
            for(int i = 0; i < telegramUserList.size(); i++) {
                sendMessage.setChatId(telegramUserList.get(i).getChatUserId());
                sendMessage.setText("You have new message: " + message);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
//            telegramUser = telegramUsersRepository.findByPhoneNumber(phoneNumber).get();
//                if (telegramUser.getStep() ==  2){

            }

    public synchronized void setButtonsEnd(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("/help");
        keyboardFirstRow.add(keyboardButton);

        keyboard.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }
    public synchronized void setButtons(SendMessage sendMessage) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("Share my number ->").setRequestContact(true);
        keyboardFirstRow.add(keyboardButton);

        keyboard.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }
    @Override
    public String getBotUsername() {
        return "fortestingmyskillsbot";
    }

    @Override
    public String getBotToken() {
        return "1065941868:AAGh2qjjI6V8AAF8BQK3zsZyUMIjKyWaiwA";
    }
}