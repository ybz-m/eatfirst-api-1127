//package jp.co.eatfirst.backendapi.middleware.aws;
//
//import com.amazonaws.AmazonClientException;
//import com.amazonaws.AmazonServiceException;
//import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
//import com.amazonaws.services.sqs.model.DeleteMessageRequest;
//import com.amazonaws.services.sqs.model.Message;
//import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
//import com.amazonaws.services.sqs.model.SendMessageRequest;
//import com.google.common.collect.Lists;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//
//@Component
//@Slf4j
//public class AmazonSQS {
//    private com.amazonaws.services.sqs.AmazonSQS amazonSQS;
//
//    @PostConstruct
//    private void initializeAmazon() {
//        this.amazonSQS = AmazonSQSClientBuilder.defaultClient();
//    }
//
//    public void push(String sqsURL, String jsonNotificationData) {
//        try {
//            log.info("===============================================");
//            log.info("Getting Started with Amazon SQS Standard Queues");
//            log.info("===============================================\n");
//
//            log.info("Sending a message to MyQueue.\n");
//            amazonSQS.sendMessage(new SendMessageRequest(sqsURL, jsonNotificationData));
//            log.info("Message Sent.\n");
//
//        }catch (final AmazonServiceException ase) {
//            log.error("Caught an AmazonServiceException, which means " +
//                    "your request made it to Amazon SQS, but was " +
//                    "rejected with an error response for some reason.");
//            log.error("Error Message:    " + ase.getMessage());
//            log.error("HTTP Status Code: " + ase.getStatusCode());
//            log.error("AWS Error Code:   " + ase.getErrorCode());
//            log.error("Error Type:       " + ase.getErrorType());
//            log.error("Request ID:       " + ase.getRequestId());
//        } catch (final AmazonClientException ace) {
//            log.error("Caught an AmazonClientException, which means " +
//                    "the client encountered a serious internal problem while " +
//                    "trying to communicate with Amazon SQS, such as not " +
//                    "being able to access the network.");
//            log.error("Error Message: " + ace.getMessage());
//        }
//    }
//
//    public List<String> receiveData(String sqsURL, String notificationUrl, String jsonNotificationData) {
//        log.info("Receiving messages from MyQueue.\n");
//        List<String> results = Lists.newArrayList();
//        final ReceiveMessageRequest receiveMessageRequest =
//                new ReceiveMessageRequest(sqsURL)
//                        .withMaxNumberOfMessages(1)
//                        .withWaitTimeSeconds(3);
//        final List<Message> messages = this.amazonSQS.receiveMessage(receiveMessageRequest)
//                .getMessages();
//        for (final com.amazonaws.services.sqs.model.Message message : messages) {
//            log.debug("Message");
//            log.debug("  MessageId:     "
//                    + message.getMessageId());
//            log.debug("  ReceiptHandle: "
//                    + message.getReceiptHandle());
//            log.debug("  MD5OfBody:     "
//                    + message.getMD5OfBody());
//            log.debug("  Body:          "
//                    + message.getBody());
//            if(!"".equals(message.getBody())) {
//                log.info("Calling POST /notification to insert records into database");
//                results.add(message.getBody());
//                System.out.println("Deleting a message.\n");
//                final String messageReceiptHandle = messages.get(0).getReceiptHandle();
//                this.amazonSQS.deleteMessage(new DeleteMessageRequest(sqsURL,
//                        messageReceiptHandle));
//            }
//        }
//        return results;
//    }
//}
