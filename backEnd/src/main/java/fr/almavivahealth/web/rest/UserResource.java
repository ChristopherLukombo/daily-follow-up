package fr.almavivahealth.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * UserResource to authenticate users.
 * @author christopher
 */
@Api(value = "User")
@RestController
@RequestMapping("/api")
public class UserResource {

}
