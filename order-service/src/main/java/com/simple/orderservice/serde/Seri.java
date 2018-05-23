package com.simple.orderservice.serde;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import com.simple.orderservice.model.OrderEvent;

import java.nio.ByteBuffer;
import java.util.Map;

public class Seri implements Serializer<OrderEvent> {


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, OrderEvent data) {
       // Person data=data1.getStock();
    	
    	

        int sizeOfOrderStatus;
        int sizeOfOrderId;
        int sizeOfProName;
        int sizeOfQty;

        byte[] serializedOrderStatus;//done
        byte[] serializedOrderId;//done
        byte[] serializedProName;//done
        byte[] serializedQty;//done
        
        

        try {
            if (data == null)
                return null;

            serializedOrderStatus=data.getStatus().toString().getBytes();
            sizeOfOrderStatus=serializedOrderStatus.length;
            
            serializedOrderId=data.getOrder().getOrderId().toString().getBytes();
            sizeOfOrderId=serializedOrderId.length;
            
            serializedProName=data.getOrder().getProName().toString().getBytes();
            sizeOfProName=serializedProName.length;


            String qty =data.getOrder().getQty()+"";
            serializedQty=qty.getBytes();
            sizeOfQty=serializedQty.length;

            ByteBuffer buf = ByteBuffer.allocate(4+4+sizeOfOrderId+4+sizeOfProName+4+sizeOfQty+4+sizeOfOrderStatus+4);
            buf.putInt((int) Integer.parseInt(data.getOrder().getOrderId()));

            buf.putInt(sizeOfOrderId);
            buf.put(serializedOrderId);

            buf.putInt(sizeOfProName);
            buf.put(serializedProName);

            buf.putInt(sizeOfQty);
            buf.put(serializedQty);

            buf.putInt(sizeOfOrderStatus);
            buf.put(serializedOrderStatus);

            return buf.array();

        } catch (Exception e) {
            throw new SerializationException("Error when serializing OrderEvent to byte[]");
        }
    }

    @Override
    public void close() {

    }
}
