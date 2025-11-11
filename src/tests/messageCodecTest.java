package tests;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.hsrm.automat_und_bank.messageCodec.Message;
import de.hsrm.automat_und_bank.messageCodec.MessageCodec;

public class messageCodecTest {
    
    @Test
    public void decodeTest(){
        Message test = new Message("Decode", 1);
        assertEquals("Decode 1", MessageCodec.decode(test));
        
    }

    @Test
    public void encodeTest(){
        String test = "encoded 2";
        Message expected = new Message("Encode", 2);
        assertEquals(expected, MessageCodec.encode(test));
    }
}
