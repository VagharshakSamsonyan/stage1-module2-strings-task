package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        String[] parts = signatureString.split(" ");

        String accessModifier = null;
        String returnType = null;
        String methodName = "";
        List<MethodSignature.Argument> arguments = new ArrayList<>();

        int index = 0;

        if (parts.length > 1 && (parts[index].equals("public") || parts[index].equals("private") || parts[index].equals("protected"))) {
            accessModifier = parts[index];
            index++;
        }

        returnType = parts[index];

        if (accessModifier==null && returnType==null){methodName = signatureString.substring(signatureString.indexOf("0"), signatureString.indexOf("("));}
        if (accessModifier==null || returnType==null){methodName = signatureString.substring(signatureString.indexOf(" ") + 1, signatureString.indexOf("("));}
        else{String outsideBrackets = signatureString.substring(signatureString.indexOf("0")+1, signatureString.indexOf("(")+1);
            methodName = outsideBrackets.substring(outsideBrackets.lastIndexOf(" ")+1, outsideBrackets.indexOf("("));
        }



        String argumentsString = signatureString.substring(signatureString.indexOf("(") + 1, signatureString.indexOf(")"));
        String[] argumentsArray = argumentsString.split("\\s*,\\s*");
        int argumentCount = argumentsArray.length;
        for (String argument : argumentsArray) {
            String[] argumentParts = argument.split(" ");
            if (argumentParts.length == 2){
                String argumentType = argumentParts[0];
                String argumentName = argumentParts[1];
                arguments.add(new MethodSignature.Argument(argumentType, argumentName));}
        }



        String newSignatureString = signatureString;
        if (accessModifier != null) {
            newSignatureString = newSignatureString.replace("null", accessModifier);
        }
        if (returnType != null) {
            newSignatureString = newSignatureString.replace("null", returnType);
        }



        return new MethodSignature(accessModifier, returnType, methodName, argumentCount, arguments);


    }
}