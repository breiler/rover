package se.cag.labs.rover.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.cag.labs.rover.service.MotorService;
import se.cag.labs.rover.bean.Movement;

import java.io.IOException;

@Api(basePath = "*", value = "Rover controller", description = "For controlling the rover", produces = "application/json")
@RestController()
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@Log4j
public class RoverController {

    @Autowired
    private MotorService motorService;

    @RequestMapping(value = "/api/status", method = RequestMethod.GET)
    @ApiOperation(value = "Fetches the rover status",
            notes = "Fetches the rover status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The status was retreived successfully"),
            @ApiResponse(code = 500, message = "Something went wrong when processing the request")
    })
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.status(HttpStatus.OK).body("Argh, rov rov rov!");
    }


    @RequestMapping(value = "/api/move", method = RequestMethod.POST)
    @ApiOperation(value = "Moves the rover",
            notes = "Fetches the rover status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The status was retreived successfully"),
            @ApiResponse(code = 500, message = "Something went wrong when processing the request")
    })
    public ResponseEntity<String> move(
            @RequestBody
            Movement movement) {
        try {
            motorService.setLeftSpeed((int) Math.round(movement.getLeft() * 255));
            motorService.setRightSpeed((int) Math.round(movement.getRight() * 255));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Argh, rov rov rov!" + movement.toString());
    }


    @RequestMapping(value = "/api/fire", method = RequestMethod.POST)
    @ApiOperation(value = "Fires the laser",
            notes = "Fires the laser")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The status was retreived successfully"),
            @ApiResponse(code = 500, message = "Something went wrong when processing the request")
    })
    public ResponseEntity<String> fire() {
        try {
            motorService.setLaser(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Argh");
    }

}
