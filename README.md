# Validation Chain

A simple library written in Kotlin that performs multiple validations, 
handling each failure case individually.

## 1. Adding the library to your project

### 1.1. Maven

```xml
<dependency>
    <groupId>com.alancamargo.validationchain</groupId>
    <artifactId>validation-chain</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 1.2. Gradle

```groovy
implementation 'com.alancamargo.validationchain:validation-chain:1.0.0'
```

## 2. Example

Let's say we're going to validate a credit card's number and expiry date:

### 2.1. Kotlin

```kotlin
fun validateCard(card: CreditCard) {
    val numberValidation = Validation(successCondition = isNumberValid(card.number),
                                      onFailureAction = { showNumberError() })
    
    val expiryDateValidation = Validation(successCondition = isExpiryDateValid(card.expiryDate),
                                          onFailureAction = { showExpiryDateError() })
    
    ValidationChain().add(numberValidation)
                     .add(expiryDateValidation)
                     .addSuccessListener(object: ValidationChain.OnSuccessListener {
                         override fun onSuccess() {
                             showSuccessMessage()
                         }
                     }).run()
}
```

### 2.2. Java

```java
public void validateCard(CreditCard card) {
    Validation numberValidation = new Validation(isNumberValid(card.getNumber()),
                                                 new Function0<Unit>() {
                                                     @Override
                                                     public Unit invoke() {
                                                         showNumberError();
                                                         return Unit.INSTANCE;
                                                     }
                                                 });
    
    Validation expiryDateValidation = new Validation(isExpiryDateValid(card.getExpiryDate()),
                                                     new Function0<Unit>() {
                                                         @Override
                                                         public Unit invoke() {
                                                             showExpiryDateError();
                                                             return Unit.INSTANCE;
                                                         }
                                                     });
    
    new ValidationChain().add(numberValidation)
                         .add(expiryDateValidation)
                         .addSuccessListener(new ValidationChain.OnSuccessListener() {
                             @Override
                             public void onSuccess() {
                                 showSuccessMessage();
                             }
                         }).run();
}
```
