package pl.damianrowinski.clients_database.exceptions;

public class ObjectInRelationshipException extends RuntimeException {
    public ObjectInRelationshipException(String message){
        super(message);
    }
}
