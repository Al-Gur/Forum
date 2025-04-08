package telran.java57.forum.accounting.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import telran.java57.forum.accounting.model.UserAccount;

public interface UserRepository extends MongoRepository<UserAccount, String> {

}
