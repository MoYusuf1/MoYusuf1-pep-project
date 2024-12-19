package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import java.util.List;

public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;
    private ObjectMapper mapper;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
        this.mapper = new ObjectMapper();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // Account endpoints
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);

        // Message endpoints
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUserHandler);

        return app;
    }

    private void registerHandler(Context ctx) {
        try {
            Account account = mapper.readValue(ctx.body(), Account.class);
            Account registeredAccount = accountService.createAccount(account);
            if(registeredAccount != null) {
                ctx.json(registeredAccount);
                ctx.status(200);
            } else {
                ctx.status(400);
            }
        } catch(Exception e) {
            ctx.status(400);
        }
    }

    private void loginHandler(Context ctx) {
        try {
            Account account = mapper.readValue(ctx.body(), Account.class);
            Account loggedInAccount = accountService.login(account);
            if(loggedInAccount != null) {
                ctx.json(loggedInAccount);
                ctx.status(200);
            } else {
                ctx.status(401);
            }
        } catch(Exception e) {
            ctx.status(401);
        }
    }

    private void createMessageHandler(Context ctx) {
        try {
            Message message = mapper.readValue(ctx.body(), Message.class);
            Message createdMessage = messageService.createMessage(message);
            if(createdMessage != null) {
                ctx.json(createdMessage);
                ctx.status(200);
            } else {
                ctx.status(400);
            }
        } catch(Exception e) {
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageByIdHandler(Context ctx) {
        try {
            int messageId = Integer.parseInt(ctx.pathParam("message_id"));
            Message message = messageService.getMessageById(messageId);
            if(message != null) {
                ctx.json(message);
            }
        } catch(Exception e) {
            ctx.status(400);
        }
    }

    private void deleteMessageHandler(Context ctx) {
        try {
            int messageId = Integer.parseInt(ctx.pathParam("message_id"));
            Message deletedMessage = messageService.deleteMessage(messageId);
            if(deletedMessage != null) {
                ctx.json(deletedMessage);
            }
        } catch(Exception e) {
            ctx.status(400);
        }
    }

    private void updateMessageHandler(Context ctx) {
        try {
            int messageId = Integer.parseInt(ctx.pathParam("message_id"));
            Message message = mapper.readValue(ctx.body(), Message.class);
            Message updatedMessage = messageService.updateMessage(messageId, message);
            if(updatedMessage != null) {
                ctx.json(updatedMessage);
            } else {
                ctx.status(400);
            }
        } catch(Exception e) {
            ctx.status(400);
        }
    }

    private void getMessagesByUserHandler(Context ctx) {
        try {
            int accountId = Integer.parseInt(ctx.pathParam("account_id"));
            List<Message> messages = messageService.getMessagesByUser(accountId);
            ctx.json(messages);
        } catch(Exception e) {
            ctx.status(400);
        }
    }
}