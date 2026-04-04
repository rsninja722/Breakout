package breakoutTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import utils.*;

class TestVelocity {
	Velocity v;
	
	@BeforeEach
	void setup() {
		v = new Velocity(new Speed(10), new Direction(0));
	}

    // setDirection
    @Test
    void setDirection_DoesCorrectCalulations() {
        v.setDirection(new Direction(90));

        assertEquals(90, v.getDirection().getDegrees());
        assertEquals(0, v.getSpeedX().getValue());
        assertEquals(10, v.getSpeedY().getValue());
    }

    // setSpeed
    @Test
    void setSpeed_DoesCorrectCalulations() {
        v.setSpeed(new Speed(20));

        assertEquals(20, v.getSpeed().getValue());
        assertEquals(20, v.getSpeedX().getValue());
        assertEquals(0, v.getSpeedY().getValue());
    }

    // reverse
    static Stream<Arguments> argsForReverse() {
    	return Stream.of(
    		Arguments.of(0,180,-10,0),
    		Arguments.of(90,270,0,-10),
    		Arguments.of(180,0,10,0),
    		Arguments.of(270,90,0,10),
    		Arguments.of(45,225,-7,-7)
		);
    }
    
    @ParameterizedTest
    @MethodSource("argsForReverse")
    void reverse_DoesCorrect180Calulations(
    		int startDir,
    		int newDir,
    		double newSpeedX, 
    		double newSpeedY
		) {
    	v.setDirection(new Direction(startDir));
    	
    	v.reverse();
    	
    	assertEquals(newDir, v.getDirection().getDegrees());
        assertEquals(newSpeedX, v.getSpeedX().getValue(), 0.01);
        assertEquals(newSpeedY, v.getSpeedY().getValue(), 0.01);
    }

    // reverseX
    static Stream<Arguments> argsForReverseX() {
    	return Stream.of(
    		Arguments.of(0,180,-10),
    		Arguments.of(180,0,10),
    		Arguments.of(90,90,0),
    		Arguments.of(45,135,-7),
    		Arguments.of(135,45,7)
		);
    }
    
    @ParameterizedTest
    @MethodSource("argsForReverseX")
    void reverseX_DoesCorrectCalulations(
    		int startDir,
    		int newDir,
    		double newSpeedX
		) {
    	v.setDirection(new Direction(startDir));
    	// Y speed should not change after reverseX
    	double oldSpeedY = v.getSpeedY().getValue();
    	
    	v.reverseX();
    	
    	assertEquals(newDir, v.getDirection().getDegrees());
        assertEquals(newSpeedX, v.getSpeedX().getValue(), 0.01);
        assertEquals(oldSpeedY, v.getSpeedY().getValue(), 0.01);
    }
}