# Application-Client-Server-Sockets
## Description

The created application exemplifies a conference organization system, for which hierarchical organization levels are taken into account. 
For this, the aspects of a Client-Server application are included:
- Direct use of the sockets of the java language is made to create the client, the administrator (a special class of client that manages 
  the permissions to which the client has access to the different nodes of the conference tree) and the server.
- Of the conferences and their hierarchy of elements is structured on n-ary tree data structures.
- By principle of code reuse, the use of generic classes was implemented.
- Creation of a proprietary communication protocol between client and server based on XML-type files.
- Communication of bit streams that represent multimedia (images), text documents (PDF Reports), and text documents with XML format.
- Management of persistence based on Json files.
- Use of external libraries in the treatment of Json and XML files.

## Structure

The application is divided into three projects, in each one there is one of the roles (permissions administrator, client, server) 
everything related to its function and form of client-server communication.

## Deployment

The server must be deployed in the first place so that it can be aware of receiving the requests made by both the client and the system 
administrator, within this application the administrator can alter the permissions (adding or removing) to the clients on the tree of 
conferences, so that although the clients can visualize the tree, they will only be able to carry out operations on it if the administrator 
grants the necessary permissions.
