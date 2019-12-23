package ru.kireev.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.kireev.AbstractHibernateTest;
import ru.kireev.api.dao.UserDao;
import ru.kireev.api.model.User;
import ru.kireev.api.service.DBServiceUser;
import ru.kireev.api.service.DBServiceUserImpl;
import ru.kireev.hibernate.dao.UserDaoHibernate;
import ru.kireev.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Демо работы с hibernate (с абстракциями) должно ")
public class WithAbstractionsTest extends AbstractHibernateTest {

  private DBServiceUser dbServiceUser;

  @BeforeEach
  @Override
  public void setUp() {
    super.setUp();
    SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
    UserDao userDao = new UserDaoHibernate(sessionManager);
    dbServiceUser = new DBServiceUserImpl(userDao);
  }

  @Test
  @DisplayName(" корректно сохранять пользователя")
  void shouldCorrectSaveUser(){
    User savedUser = buildDefaultUser();
    long id = dbServiceUser.saveUser(savedUser);
    User loadedUser = loadUser(id);

    assertThat(loadedUser).isNotNull().isEqualToComparingFieldByField(savedUser);

    System.out.println(savedUser);
    System.out.println(loadedUser);
  }

  @Test
  @DisplayName(" корректно загружать пользователя")
  void shouldLoadCorrectUser(){
    User savedUser = buildDefaultUser();
    saveUser(savedUser);

    Optional<User> mayBeUser = dbServiceUser.getUser(savedUser.getId());

    assertThat(mayBeUser).isPresent().get().isEqualToComparingFieldByField(savedUser);

    System.out.println(savedUser);
    mayBeUser.ifPresent(System.out::println);
  }

  @Test
  @DisplayName(" корректно изменять ранее сохраненного пользователя")
  void shouldCorrectUpdateSavedUser(){
    User savedUser = buildDefaultUser();
    saveUser(savedUser);

    User savedUser2 = new User(savedUser.getId(), TEST_USER_NEW_NAME);
    long id = dbServiceUser.saveUser(savedUser2);
    User loadedUser = loadUser(id);

    assertThat(loadedUser).isNotNull().hasFieldOrPropertyWithValue(FIELD_ID, id)
        .isEqualToComparingOnlyGivenFields(savedUser2, FIELD_NAME);

    System.out.println(savedUser);
    System.out.println(savedUser2);
    System.out.println(loadedUser);
  }

}
