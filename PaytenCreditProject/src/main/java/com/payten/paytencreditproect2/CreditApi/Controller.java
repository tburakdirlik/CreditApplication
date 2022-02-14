package com.payten.paytencreditproect2.CreditApi;
import com.payten.paytencreditproect2.Exception.ExceptionType;
import com.payten.paytencreditproect2.Exception.IDBelongToSomeoneException;
import com.payten.paytencreditproect2.Exception.RequestHasNotIDException;
import com.payten.paytencreditproect2.Exception.UserNotFoundException;
import com.payten.paytencreditproect2.SmsApi.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final UserRepository userRepository;
    private Service service;
    private StarterSmsSender starterSmsSender;

    public void addUserCheckException(UserRequest userRequest){
        if (userRequest.getCreditLimitMultiplier()  == null ){
            userRequest.setCreditLimitMultiplier(4);
        }
        if (userRequest.getUserId() == null){
            throw new RequestHasNotIDException(ExceptionType.ID_NOTEXIST);
        }
        if (userRepository.findById(userRequest.getUserId()).isPresent()){
            throw new IDBelongToSomeoneException(ExceptionType.ID_EXIST);
        }
    }

    @PostMapping("/addUser")
    @ResponseStatus(HttpStatus.CREATED)
    public String addUser(@RequestBody UserRequest userReceivedDto){

        addUserCheckException(userReceivedDto);

        UserRequest user = UserRequest.builder()
                .userId(userReceivedDto.getUserId())
                .name(userReceivedDto.getName())
                .surname(userReceivedDto.getSurname())
                .telephoneNo(userReceivedDto.getTelephoneNo())
                .monthlyIncome(userReceivedDto.getMonthlyIncome())
                .creditScore(userReceivedDto.getCreditScore())
                .creditLimitMultiplier(userReceivedDto.getCreditLimitMultiplier())
                .build();

        UserDbEntity userWillBeRecorded = createUserDbEntityWithCreditResult(user);
        userRepository.save(userWillBeRecorded);

            starterSmsSender = new StarterSmsSender();         // Starting the sms api.
            starterSmsSender.sendMessage(userWillBeRecorded);  // Sending sms.

        return "User added which has " + user.getUserId() + " user ID";
    }

    public void calculateCreditResult(UserRequest userRequest){
        service = Service.builder()
                .monthlyIncome(userRequest.getMonthlyIncome())
                .creditScore(userRequest.getCreditScore())
                .creditLimitMultiplier(userRequest.getCreditLimitMultiplier())
                .build();

        service.calculete(service.getMonthlyIncome(), service.getCreditScore(), service.getCreditLimitMultiplier());
    }


    public UserDbEntity createUserDbEntityWithCreditResult(UserRequest user){
        //createUserDbEntityWithCreditResult is helper method for addUser method
        calculateCreditResult(user);

        UserDbEntity userDbEntity = UserDbEntity.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .surname(user.getSurname())
                .telephoneNo(user.getTelephoneNo())
                .monthlyIncome(user.getMonthlyIncome())
                .creditScore(user.getCreditScore())
                .creditLimitMultiplier(user.getCreditLimitMultiplier())
                .creditResult(service.getCreditResult())
                .creditLimit(service.getCreditLimit())
                .build();

        return userDbEntity;

    }

    @DeleteMapping("/deleteUser/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser(@PathVariable Long userId){

        if (userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
            return "User with ID " +  userId +  " has been deleted";
        }
        else {
            throw  new UserNotFoundException(ExceptionType.USER_NOTFOUND);
        }
    }

    @GetMapping("/getUserInfos/{userId}")
    public UserDbEntity getUserInfos(@PathVariable Long userId){

        if (userRepository.findById(userId).isPresent()){
            Optional<UserDbEntity> getUserInfos = userRepository.findById(userId);
            return getUserInfos.get();
        }
        else { // buradan hata fırlat else durumunda
            throw  new UserNotFoundException(ExceptionType.USER_NOTFOUND);
        }
    }

    @GetMapping("/getCreditResult/{userId}")
    public String getCreditResult(@PathVariable Long userId){

        String answer="";

        if (userRepository.findById(userId).isPresent()){
            Optional<UserDbEntity> getUserCreditResult = userRepository.findById(userId);

                if (getUserCreditResult.get().getCreditResult().equals("DENIED")){
                    answer = "YOUR APPLICATION IS "+getUserCreditResult.get().getCreditResult();
                }
                if (getUserCreditResult.get().getCreditResult().equals("APPROVED")){
                    answer = "YOUR APPLICATION IS "+getUserCreditResult.get().getCreditResult() + " "+
                            getUserCreditResult.get().getCreditLimit() + "TL LIMIT ASSIGNED";
            }
        }
        else {
            throw new UserNotFoundException(ExceptionType.USER_NOTFOUND);
        }
        return  answer;
    }


    @PostMapping("/updateUser/{userId}")
    public String updateUser(@RequestBody UserRequest userReceivedDto, @PathVariable Long userId) {

        String response="";
        if (userRepository.findById(userId).isPresent()){

            //User fields other than UserId is considered updateable.
            Optional<UserDbEntity> userToBeUpdated = userRepository.findById(userId);
            Double firstCreditLimit = userToBeUpdated.get().getCreditLimit();
            String firstCreditResult = userToBeUpdated.get().getCreditResult();

            //Check the changes with if statements.
            if (userReceivedDto.getName() != null){
                if (userReceivedDto.getName() != userToBeUpdated.get().getName()){
                    userToBeUpdated.get().setName(userReceivedDto.getName());
                }
            }
            if (userReceivedDto.getSurname() != null){
                if (userReceivedDto.getSurname() != userToBeUpdated.get().getSurname()){
                    userToBeUpdated.get().setSurname(userReceivedDto.getSurname());
                }

            }
            if (userReceivedDto.getTelephoneNo() != null){
                if (userReceivedDto.getTelephoneNo() != userToBeUpdated.get().getTelephoneNo()){
                    userToBeUpdated.get().setTelephoneNo(userReceivedDto.getTelephoneNo());
                }
            }
            if (userReceivedDto.getMonthlyIncome() != null){
                if (userReceivedDto.getMonthlyIncome() != userToBeUpdated.get().getMonthlyIncome()){
                    userToBeUpdated.get().setMonthlyIncome(userReceivedDto.getMonthlyIncome());
                }
            }
            if (userReceivedDto.getCreditScore() != null){
                if (userReceivedDto.getCreditScore() != userToBeUpdated.get().getCreditScore()){
                    userToBeUpdated.get().setCreditScore(userReceivedDto.getCreditScore());
                }
            }
            if (userReceivedDto.getCreditLimitMultiplier() != null){
                if (userReceivedDto.getCreditLimitMultiplier() != userToBeUpdated.get().getCreditLimitMultiplier()){
                    userToBeUpdated.get().setCreditLimitMultiplier(userReceivedDto.getCreditLimitMultiplier());
                }
            }

            calculateCreditResult(userReceivedDto);
            userToBeUpdated.get().setCreditResult(service.getCreditResult());
            userToBeUpdated.get().setCreditLimit(service.getCreditLimit());
            userRepository.save(userToBeUpdated.get()); //The update is saved in the database

            //After the update, if the credit result or limit changes, and sms is sent to the user.
            if (firstCreditLimit  != userToBeUpdated.get().getCreditLimit() || firstCreditResult != userToBeUpdated.get().getCreditResult()){
                starterSmsSender = new StarterSmsSender();
                starterSmsSender.setPhoneMessage("YOUR CREDIT APPLICATION HAS BEEN UPDATED. ");
                starterSmsSender.sendMessage(userToBeUpdated.get());
            }
            response = "User with ID " + userId + " is updated.";
        }
        else {
            throw new UserNotFoundException(ExceptionType.USER_NOTFOUND);
        }
        return response;
    }
}