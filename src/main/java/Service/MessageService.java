package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public Message createMessage(Message message) {
        if(message.getMessage_text() == null || 
           message.getMessage_text().trim().isEmpty() || 
           message.getMessage_text().length() > 254) {
            return null;
        }
        if(accountDAO.getAccountById(message.getPosted_by()) == null) {
            return null;
        }
        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public List<Message> getMessagesByUser(int userId) {
        return messageDAO.getMessagesByUser(userId);
    }

    public Message deleteMessage(int messageId) {
        Message message = messageDAO.getMessageById(messageId);
        if(message != null && messageDAO.deleteMessage(messageId)) {
            return message;
        }
        return null;
    }

    public Message updateMessage(int messageId, Message message) {
        if(message.getMessage_text() == null || 
           message.getMessage_text().trim().isEmpty() || 
           message.getMessage_text().length() > 254) {
            return null;
        }
        Message existingMessage = messageDAO.getMessageById(messageId);
        if(existingMessage != null && messageDAO.updateMessage(messageId, message.getMessage_text())) {
            return messageDAO.getMessageById(messageId);
        }
        return null;
    }
}