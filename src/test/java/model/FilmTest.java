package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;

@DisplayName("Фильм")
public class FilmTest {
    @Test
    @DisplayName("Должен создать фильм")
    void shouldCreatFilm() {
        Film filmNew = Film.builder()
                .name("Джентельмены")
                .description("Комедийный боевки")
                .releaseDate(LocalDate.of(2019, 12, 3))
                .duration(113)
                .build();
        FilmController filmController = new FilmController();
        Film filmCreate = filmController.create(filmNew);
        Assertions.assertNotNull(filmCreate, "Фильм не создан");
    }

    @Test
    @DisplayName("Должен вернуть ошибку отсутствия Name")
    void shouldFailName() {
        Film filmNew = Film.builder()
                .description("Комедийный боевки")
                .releaseDate(LocalDate.of(2019, 12, 3))
                .duration(113)
                .build();
        FilmController filmController = new FilmController();
        Assertions.assertThrows(ValidationException.class, () -> {
            filmController.create(filmNew);
        }, "Исключения об некорректном Name нет");
    }

    @Test
    @DisplayName("Должен вернуть ошибку превышение длины Description")
    void shouldFailDescription() {
        Film filmNew = Film.builder()
                .name("Джентельмены")
                .description("комедийный боевик 2019 года, одиннадцатый полнометражный фильм британского кинорежиссёра Гая Ричи, который разработал сюжет вместе с Айвеном Аткинсоном и Марн Дэвис. В фильме снимались Мэттью Макконахи, Чарли Ханнэм, Генри Голдинг, Мишель Докери, Джереми Стронг, Эдди Марсан, Колин Фаррелл и Хью Грант. В нем рассказывается об американском оптовике каннабиса в Англии, который хочет продать свой бизнес, запуская цепочку шантажа и схем, направленных на его подрыв.")
                .releaseDate(LocalDate.of(2019, 12, 3))
                .duration(113)
                .build();
        FilmController filmController = new FilmController();
        Assertions.assertThrows(ValidationException.class, () -> {
            filmController.create(filmNew);
        }, "Исключения об превышение длины Description нет");
    }
}
