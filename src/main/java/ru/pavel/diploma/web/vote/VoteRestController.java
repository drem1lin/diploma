package ru.pavel.diploma.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.pavel.diploma.View;
import ru.pavel.diploma.model.Vote;
import ru.pavel.diploma.service.VoteService;
import ru.pavel.diploma.web.SecurityUtil;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    static final String REST_URL = "/rest/vote";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService service;

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get vote {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete vote {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    @GetMapping
    public List<Vote> getAll() {
        log.info("getAll for vote");
        return service.getAll(SecurityUtil.authUserId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Vote vote, @RequestParam Integer restaurantId) {
        log.info("update restaurant {}", vote);
        service.update(vote, SecurityUtil.authUserId(), restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@Validated(View.Web.class) @RequestBody Vote vote, @RequestParam Integer restaurantId) {
        Vote created = service.create(vote, SecurityUtil.authUserId(), restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL  + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}