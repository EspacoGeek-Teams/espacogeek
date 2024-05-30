package com.espacogeek.geek.services.User;

import com.espacogeek.geek.exception.GenericException;
import com.espacogeek.geek.modals.UserModal;

import java.util.List;
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
     * @throws GenericException Http Code 409 Conflict if an email already exist. 
     * @throws GenericException Http Code 404 BAD REQUEST if a field is too long.
     */
    UserModal save(UserModal user) throws GenericException;
    
    /**
     * Retrieves a UserModal object by its id, username (partial match) and Email.
     * 
     * @param id The id of the UserModal object to be retrieved.
     * @param username The username (partial) of the UserModal object to be retrieved.
     * @param email The email of the UserModal object to be retrieved.
     * @return An Optional containing the UserModal object if found, otherwise an empty Optional.
     */
    List<Optional<UserModal>> findByIdOrUsernameContainsOrEmail(Integer id, String username, String email);

    /**
     * Retrieves a UserModal object by its ID.
     * 
     * @param id The ID of the UserModal object to be retrieved.
     * @return An Optional containing the UserModal object if found, otherwise an empty Optional.
     */
    Optional<UserModal> findById(Integer id);

    /**
     * Delete user by Id provide.
     * 
     * @param id
     */
    void deleteById(Integer id);
}
