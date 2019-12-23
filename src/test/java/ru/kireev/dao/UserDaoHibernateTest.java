package ru.kireev.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.kireev.AbstractHibernateTest;
import ru.kireev.api.model.User;
import ru.kireev.hibernate.dao.UserDaoHibernate;
import ru.kireev.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с пользователями должно ")
class UserDaoHibernateTest extends AbstractHibernateTest {

  private SessionManagerHibernate sessionManagerHibernate;
  private UserDaoHibernate userDaoHibernate;

  @BeforeEach
  @Override
  public void setUp() {
    super.setUp();
    sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
    userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
  }

  @Test
  @DisplayName(" корректно загружать пользователя по заданному id")
  void shouldFindCorrectUserById() {
    User expectedUser = new User(0, "Вася");
    saveUser(expectedUser);

    assertThat(expectedUser.getId()).isGreaterThan(0);

    sessionManagerHibernate.beginSession();
    Optional<User> mayBeUser = userDaoHibernate.findById(expectedUser.getId());
    sessionManagerHibernate.commitSession();

    assertThat(mayBeUser).isPresent().get().isEqualToComparingFieldByField(expectedUser);
  }

  @DisplayName(" корректно сохранять пользователя")
  @Test
  void shouldCorrectSaveUser() {
    User expectedUser = new User(0, "Вася");
    sessionManagerHibernate.beginSession();
    long id = userDaoHibernate.saveUser(expectedUser);
    sessionManagerHibernate.commitSession();

    assertThat(id).isGreaterThan(0);

    User actualUser = loadUser(id);
    assertThat(actualUser).isNotNull().hasFieldOrPropertyWithValue("name", expectedUser.getName());

    expectedUser = new User(id, "Не Вася");
    sessionManagerHibernate.beginSession();
    long newId = userDaoHibernate.saveUser(expectedUser);
    sessionManagerHibernate.commitSession();

    assertThat(newId).isGreaterThan(0).isEqualTo(id);
    actualUser = loadUser(newId);
    assertThat(actualUser).isNotNull().hasFieldOrPropertyWithValue("name", expectedUser.getName());

  }

  @DisplayName(" возвращать менеджер сессий")
  @Test
  void getSessionManager() {
    assertThat(userDaoHibernate.getSessionManager()).isNotNull().isEqualTo(sessionManagerHibernate);
  }

}
