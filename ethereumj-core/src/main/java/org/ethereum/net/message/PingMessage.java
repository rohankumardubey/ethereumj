package org.ethereum.net.message;

import static org.ethereum.net.Command.PING;

import org.ethereum.net.Command;
import org.spongycastle.util.encoders.Hex;

/**
 * www.ethereumJ.com
 * @author: Roman Mandeleil
 * Created on: 06/04/14 14:56
 */
public class PingMessage extends Message {

	/** Ping message is always a the same single command payload */ 
	private static byte[] FIXED_PAYLOAD = Hex.decode("C102");

    public byte[] getEncoded() {
        return FIXED_PAYLOAD;
    }

	@Override
	public Command getCommand() {
		return PING;
	}

    @Override
    public Class<PongMessage> getAnswerMessage() {
        return PongMessage.class;
    }
    
    @Override
    public String toString() {
    	return "[command=" + getCommand().name() + "]";
    }
}