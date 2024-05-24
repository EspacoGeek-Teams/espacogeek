package com.espacogeek.geek.services.User;

import com.espacogeek.geek.exception.GenericExeption;
import com.espacogeek.geek.modals.UserModal;

import java.util.Optional;

/**
 * This interface defines the contract for the User Service.
 * It provides methods to perform CRUD operations on UserModal objects.
 */
public interface UserService {
    
    /**
     * Saves a new UserModal object to the database.
     * 
     * @param user The UserModal object to be saved.
     * @return The saved UserModal object.
     * @throws GenericExeption If an error occurs during the save operation.
     */
    UserModal save(UserModal user) throws GenericExeption;
    
    /**
     * Retrieves a UserModal object by its email address.
     * 
     * @param email The email address of the UserModal object to be retrieved.
     * @return An Optional containing the UserModal object if found, otherwise an empty Optional.
     * @throws GenericExeption If an error occurs during the retrieval operation with status 404.
     */
    Optional<UserModal> findByEmail(String email) throws GenericExeption;
    
    /**
     * Retrieves a UserModal object by its username (partial match).
     * 
     * @param username The username (partial) of the UserModal object to be retrieved.
     * @return An Optional containing the UserModal object if found, otherwise an empty Optional.
     * @throws GenericExeption If an error occurs during the retrieval operation with status 404.
     */
    Optional<UserModal> findByUsernameContains(String username) throws GenericExeption;

    /**
     * Retrieves a UserModal object by its ID.
     * 
     * @param id The ID of the UserModal object to be retrieved.
     * @return An Optional containing the UserModal object if found, otherwise an empty Optional.
     * @throws GenericExeption If an error occurs during the retrieval operation with status 404.
     */
    Optional<UserModal> findById(Integer id) throws GenericExeption;
}
