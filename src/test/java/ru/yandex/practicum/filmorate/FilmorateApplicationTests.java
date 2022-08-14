package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.MPARatingDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.time.Duration;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FilmorateApplicationTests {

	User user1 = new User( "user1email",
			"user1login",
			"user1name",
			LocalDate.of(1960,10, 1));
	User user2 = new User( "user2email",
			"user2login",
			"user2name",
			LocalDate.of(1961,9, 2));
	User user3 = new User( "user3email",
			"user3login",
			"user3name",
			LocalDate.of(1962,8, 3));
	User user4 = new User( 1,"user3email",
			"user4login",
			"user4name",
			LocalDate.of(1963,8, 3));
	Film film1 = new Film(1,
			"film1name",
			"film1description",
			LocalDate.of(1980, 2, 2),
			Duration.ofMinutes(20),
			Film.MPARating.G,
			EnumSet.noneOf(Film.Genre.class));

	Film film2 = new Film(2,
			"film2name",
			"film2description",
			LocalDate.of(1981, 2, 2),
			Duration.ofMinutes(69),
			Film.MPARating.NC_17,
			EnumSet.of(Film.Genre.COMEDY, Film.Genre.CARTOON));
	Film film3 = new Film(1,
			"film3name",
			"film3description",
			LocalDate.of(1982, 2, 2),
			Duration.ofMinutes(90),
			Film.MPARating.PG_13,
			EnumSet.of(Film.Genre.ACTION));

	/*@BeforeEach
	public void contextLoads() {

	}*/

	private final UserDbStorage userStorage;
	private final FilmDbStorage filmStorage;
	private final MPARatingDbStorage ratingDbStorage;
	public void setUsualUserValues() {
		user1 = userStorage.get(1).get();
		user2 = userStorage.get(2).get();
		user3 = userStorage.get(3).get();
	}
	public void setUsualFilmValues() {
		film1 = filmStorage.get(1).get();
		film2 = filmStorage.get(2).get();
		//film3 = filmStorage.get(3).get();
	}
	@Test
	@Order(1)
	public void testFindUserById() {
		userStorage.create(user1);
		Optional<User> userOptional1 = userStorage.get(1);

		assertThat(userOptional1)
				.isPresent()
				.hasValueSatisfying(user ->
						assertThat(user).hasFieldOrPropertyWithValue("id", 1L))
				.hasValueSatisfying( user ->
						assertThat(user).hasFieldOrPropertyWithValue("login", "user1login"))
				.hasValueSatisfying( user ->
						assertThat(user).hasFieldOrPropertyWithValue("email", "user1email"))
				.hasValueSatisfying( user ->
						assertThat(user).hasFieldOrPropertyWithValue("name", "user1name"))
				.hasValueSatisfying( user ->
						assertThat(user).hasFieldOrPropertyWithValue("birthday",
								LocalDate.of(1960,10, 1)))
				.hasValueSatisfying(user ->
						assertThat(user).hasFieldOrPropertyWithValue("friends", Set.of()));
		user1 = userOptional1.get();

		userStorage.create(user2);
		Optional<User> userOptional2 = userStorage.get(2);
		assertThat(userOptional2)
				.isPresent()
				.hasValueSatisfying(user ->
						assertThat(user).hasFieldOrPropertyWithValue("id", 2L))
				.hasValueSatisfying( user ->
						assertThat(user).hasFieldOrPropertyWithValue("login", "user2login"))
				.hasValueSatisfying( user ->
						assertThat(user).hasFieldOrPropertyWithValue("email", "user2email"))
				.hasValueSatisfying( user ->
						assertThat(user).hasFieldOrPropertyWithValue("name", "user2name"))
				.hasValueSatisfying( user ->
						assertThat(user).hasFieldOrPropertyWithValue("birthday",
								LocalDate.of(1961,9, 2)))
				.hasValueSatisfying(user ->
						assertThat(user).hasFieldOrPropertyWithValue("friends", Set.of()));
		user2 = userOptional2.get();
		userStorage.create(user3);
		Optional<User> userOptional3 = userStorage.get(3);
		user3 = userOptional3.get();

	}
	@Test
	@Order(6)
	public void testReadAllUsers() {
		setUsualUserValues();
		assertThat(userStorage.get()).contains(user1, user2, user3);
	}

	@Test
	@Order(7)
	public void testUpdateUser() {
		setUsualUserValues();
		userStorage.update(user4);
		Optional<User> optionalUser = userStorage.get(1);
		assertThat(optionalUser).isPresent().hasValueSatisfying( user ->
				assertThat(user).hasFieldOrPropertyWithValue("login", "user4login"));
	}
	@Test
	@Order(3)
	public void testFriend() {
		setUsualUserValues();
		userStorage.addToFriends(user1.getId(), user2.getId());
		Set<User> friends = userStorage.getFriends(user1.getId());
		assertThat(friends).contains(user2).doesNotContain(user3);
		Set<User> friends2 = userStorage.getFriends(user2.getId());
		assertThat(friends2).doesNotContain(user1);
		userStorage.addToFriends(user3.getId(), user2.getId());
		Set<User> commonFriends = userStorage.getCommonFriends(user1.getId(), user3.getId());
		assertThat(commonFriends).contains(user2);
	}

	@Test
	@Order(4)
	public void testFilmFindById() {
		filmStorage.create(film1);
		Optional<Film> optionalFilm1 = filmStorage.get(1);
		assertThat(optionalFilm1)
				.isPresent()
				.hasValueSatisfying(user ->
						assertThat(user).hasFieldOrPropertyWithValue("id", 1L))
				.hasValueSatisfying( user ->
						assertThat(user).hasFieldOrPropertyWithValue("name", "film1name"))
				.hasValueSatisfying( user ->
						assertThat(user).hasFieldOrPropertyWithValue("description", "film1description"))
				.hasValueSatisfying( user ->
						assertThat(user).hasFieldOrPropertyWithValue("releaseDate",
								LocalDate.of(1980, 2, 2)))
				.hasValueSatisfying( user ->
						assertThat(user).hasFieldOrPropertyWithValue("duration",
								Duration.ofMinutes(20)))
				.hasValueSatisfying(user ->
						assertThat(user).hasFieldOrPropertyWithValue("mpaRating", Film.MPARating.G));
		film1 = optionalFilm1.get();

		filmStorage.create(film2);
		film2 = filmStorage.get(2).get();

	}

	@Test
	@Order(5)
	public void testLikes() {
		filmStorage.setLike(1, 1);
		filmStorage.setLike(1, 2);
		filmStorage.setLike(2, 3);
		List<Film> mostLiked = filmStorage.getMostLiked(10);
		assertThat(mostLiked).first().hasFieldOrPropertyWithValue("id", 1L);
		filmStorage.deleteLike(1, 1);
		filmStorage.setLike(2, 1);
		mostLiked = filmStorage.getMostLiked(10);
		assertThat(mostLiked).first().hasFieldOrPropertyWithValue("id", 2L);
	}

	@Test
	public void testEnum(){
		assertThat(ratingDbStorage.get()).contains(Film.MPARating.G,
				Film.MPARating.PG_13,
				Film.MPARating.NC_17,
				Film.MPARating.PG,
				Film.MPARating.R);
		assertThat(ratingDbStorage.get(1)).isPresent()
				.hasValueSatisfying(rating -> assertThat(rating)
						.hasFieldOrPropertyWithValue("localizedName", "G"));
	}
}
