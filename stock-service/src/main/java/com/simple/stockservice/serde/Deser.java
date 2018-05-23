package com.simple.stockservice.serde;

import org.apache.kafka.common.errors.SerializationException;
import com.simple.stockservice.model.OrderEvent;
import com.simple.stockservice.model.OrderEventType;
import com.simple.stockservice.model.Orders;

import java.nio.ByteBuffer;
import java.util.Map;

public class Deser implements org.apache.kafka.common.serialization.Deserializer<OrderEvent> {


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public OrderEvent deserialize(String topic, byte[] data) {

        String encoding="UTF8";
        
        try {
            if (data == null){
                System.out.println("Null recieved at deserialize");
                return null;
            }

            ByteBuffer buf = ByteBuffer.wrap(data);
            

            int sizeOfOrderStatus;
            int sizeOfOrderId;
            int sizeOfProName;
            int sizeOfQty;
            
            OrderEventType newstatus=OrderEventType.CREATED;
            
            
            byte[] bytesOrderId;//done
            byte[] bytesProName;//
            byte[] bytesQty;//
            byte[] bytesOrderStatus;//
            
            //orderid
            sizeOfOrderId=buf.getInt();
            bytesOrderId=new byte[sizeOfOrderId];
            buf.get(bytesOrderId);
            String deserializedOrderId=new String(bytesOrderId,encoding);
            
            //proname
            sizeOfProName=buf.getInt();
            bytesProName=new byte[sizeOfProName];
            buf.get(bytesProName);
            String deserializedProName=new String(bytesProName,encoding);

            //qty
            sizeOfQty=buf.getInt();
            bytesQty=new byte[sizeOfQty];
            buf.get(bytesQty);
            int deserializeQty=Integer.parseInt(new String(bytesQty,encoding));
            
            //orderstatus
            sizeOfOrderStatus=buf.getInt();
            bytesOrderStatus=new byte[sizeOfOrderStatus];
            buf.get(bytesOrderStatus);
            String deserializedOrderStatus=new String(bytesOrderStatus,encoding);
            
            if(OrderEventType.APRROVED.toString().equals(deserializedOrderStatus)) {
            	newstatus=OrderEventType.APRROVED;
            	
            }else if(OrderEventType.CREATED.toString().equals(deserializedOrderStatus)) {
            	newstatus=OrderEventType.CREATED;
            	
            }else if(OrderEventType.REJECTED.toString().equals(deserializedOrderStatus)) {
            	newstatus=OrderEventType.REJECTED;
            	
            }


           return new OrderEvent(new Orders(deserializedOrderId,deserializedProName,deserializeQty),newstatus);



        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to OrderEvent");
        }
    }

    @Override
    public void close() {

    }
}
