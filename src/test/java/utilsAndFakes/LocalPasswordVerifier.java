package utilsAndFakes;

import clientSide.services.PasswordGenerator;
import common.entities.User;
import common.repository.IUserRepository;

public class LocalPasswordVerifier {
    private final IUserRepository userRepository;
    private final PasswordGenerator passwordGenerator;

    public LocalPasswordVerifier(IUserRepository userRepository, PasswordGenerator passwordGenerator) {
        this.userRepository = userRepository;
        this.passwordGenerator = passwordGenerator;
    }

    public boolean verifyUserAuthentification(String pseudonym, String password) {
        User searchedUser = userRepository.getUserByPseudonym(pseudonym);
        if (searchedUser == null) {
            return false;
        }
        String givenHashedPassword = passwordGenerator.hashPassword(password, searchedUser.getSalt());
        return givenHashedPassword.equals(searchedUser.getPassword());
    }
}

