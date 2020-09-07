package ru.pavel.diploma.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.pavel.diploma.DishTestData;
import ru.pavel.diploma.model.Dish;
import ru.pavel.diploma.service.DishService;
import ru.pavel.diploma.util.exception.NotFoundException;
import ru.pavel.diploma.web.AbstractControllerTest;
import ru.pavel.diploma.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.pavel.diploma.DishTestData.*;
import static ru.pavel.diploma.RestaurantTestData.THE_CASTLE_ID;
import static ru.pavel.diploma.TestUtil.readFromJson;
import static ru.pavel.diploma.TestUtil.userHttpBasic;
import static ru.pavel.diploma.UserTestData.*;
import static ru.pavel.diploma.util.exception.ErrorType.VALIDATION_ERROR;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = DishRestController.REST_URL + '/';

    @Autowired
    private DishService dishService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + THE_CASTLE_ID + '/' +HAMBURGER_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(HAMBURGER));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + THE_CASTLE_ID + '/' + HAMBURGER_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + THE_CASTLE_ID + '/' + DishTestData.NOT_FOUND)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + THE_CASTLE_ID + '/' + HAMBURGER_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> dishService.get(HAMBURGER_ID, THE_CASTLE_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + THE_CASTLE_ID + '/' + DishTestData.NOT_FOUND)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + THE_CASTLE_ID + '/' + HAMBURGER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(dishService.get(HAMBURGER_ID, THE_CASTLE_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Dish dish = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + THE_CASTLE_ID + '/')
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dish))
                .with(userHttpBasic(ADMIN)));

        Dish created = readFromJson(action, Dish.class);
        int newId = created.id();
        dish.setId(newId);
        DISH_MATCHER.assertMatch(created, dish);
        DISH_MATCHER.assertMatch(dishService.get(newId, THE_CASTLE_ID), dish);
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL+ THE_CASTLE_ID + '/')
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(THE_CASTLE_DISHES));
    }

    @Test
    void filter() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + THE_CASTLE_ID + '/' + "filter")
                .param("date", "2020-01-30")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(DISH_MATCHER.contentJson(THE_CASTLE_DISHES));
    }

    @Test
    void filterAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + THE_CASTLE_ID + '/' + "filter?date=")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(DISH_MATCHER.contentJson());
    }

    @Test
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, null, null, 0);
        perform(MockMvcRequestBuilders.post(REST_URL+ THE_CASTLE_ID + '/')
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateInvalid() throws Exception {
        Dish invalid = new Dish(HAMBURGER_ID, null, null, 0);
        perform(MockMvcRequestBuilders.put(REST_URL + THE_CASTLE_ID + '/' + HAMBURGER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }
}