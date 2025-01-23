# Project: Social Media Blog API

## Background 

When building a full-stack application, we're typically concerned with both a front end, that displays information to the user and takes in input, and a backend, that manages persisted information.

This project will be a backend for a hypothetical social media app, where we must manage our users’ accounts as well as any messages that they submit to the application. The application will function as a micro-blogging or messaging app. In our hypothetical application, any user should be able to see all of the messages posted to the site, or they can see the messages posted by a particular user. In either case, we require a backend which is able to deliver the data needed to display this information as well as process actions like logins, registrations, message creations, message updates, and message deletions.

### Technology Stack

- Language: Java
- Data Access: JDBC
- Database: SQL
- Architecture: RESTful API, 3-Layer Architecture

## Database Tables 

These will be provided in a sql script, and a ConnectionUtil class that will run the sql script is provided:

### Account
```
account_id integer primary key auto_increment,
username varchar(255) unique,
password varchar(255)
```

### Message
```
message_id integer primary key auto_increment,
posted_by integer,
message_text varchar(255),
time_posted_epoch long,
foreign key (posted_by) references Account(account_id)
```
## Key Endpoints
```
- POST /register: User registration
- POST /login: User authentication
- POST /messages: Create new message
- GET /messages: Retrieve all messages
- GET /messages/{message_id}: Get specific message
- DELETE /messages/{message_id}: Delete message
- PATCH /messages/{message_id}: Update message
- GET /accounts/{account_id}/messages: Get user's messages
```

## Validation Rules
### Registration

1. Username cannot be blank
2. Password minimum 4 characters
3. Unique username required

### Message Creation

1. Non-empty message
2. Message
