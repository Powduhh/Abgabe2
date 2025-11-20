package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.hsrm.automat_und_bank.messageCodec.Message;
import de.hsrm.automat_und_bank.messageCodec.MessageCodec;
import de.hsrm.automat_und_bank.messageCodec.MessageKARTE;
import de.hsrm.automat_und_bank.messageCodec.MessageType;

public class messageCodecTest {
    
    @Test
    public void encodeTestIntoSpecificMessage(){
        Message test = new MessageKARTE("Karte", 1);
        assertEquals("KarteEncode 1", MessageCodec.encode(test));
        assertEquals(MessageType.KARTE, test.getType());
        
    }

    @Test
    public void decodeTest(){
        String test = "KarteDecode 2";
        Message actual = MessageCodec.decode(test);
        Message expected = new MessageKARTE("KarteDecode", 2);
        assertTrue(actual.equals(expected));
    }
}

