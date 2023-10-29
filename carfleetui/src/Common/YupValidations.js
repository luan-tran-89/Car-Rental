import * as Yup from "yup";
import { styled } from "@mui/material/styles";

export const CustomErrorDiv = styled("div")({
    color: "red",
});

export const signUpValidationSchema = {
    firstName: Yup.string()
        .min(2, "Must be 2 characters or more")
        .required("Required"),
    lastName: Yup.string()
        .min(2, "Must be 2 characters or more")
        .required("Required"),
    userName: Yup.string()
        .min(3, "Must be 3 characters or more")
        .required("Required"),
    phone: Yup.string()
        .length(10, "Phone number must be exactly 10 characters")
        .matches(/^\d+$/, "Phone number must only contain digits")
        .required("Required"),
    email: Yup.string().email("Invalid email address").required("Required"),
    password: Yup.string()
        .min(3, "Must be at least 3 characters")
        // .matches(/[A-Z]/, "Must contain at least one uppercase letter")
        // .matches(/[a-z]/, "Must contain at least one lowercase letter")
        // .matches(/[0-9]/, "Must contain at least one number")
        // .matches(
        //     /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/,
        //     "Must contain at least one special character"
        // )
        .required("Required"),
    confirmpassword: Yup.string()
        .oneOf([Yup.ref("password"), null], "Passwords must match")
        .required("Confirm password is required"),
};

export const signInValidationSchema = {

    email: Yup.string().email("Invalid email address").required("Required"),
    password: Yup.string()
        .min(3, "Must be at 3 least  characters")
        // .matches(/[A-Z]/, "Must contain at least one uppercase letter")
        // .matches(/[a-z]/, "Must contain at least one lowercase letter")
        // .matches(/[0-9]/, "Must contain at least one number")
        // .matches(
        //     /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/,
        //     "Must contain at least one special character"
        // )
        .required("Required"),

}

export const addCardValidationSchema = {
   
    cardNumber: Yup.string()
    .length(16, "Card number must be exactly 16 digits")
    .required("Required"),
    expiryDate: Yup.string()
    .min(7, "MM/YYYY")
    .required("Required"),
    cardType: Yup.string()
    .required("Required"),
    cvv: Yup.string()
    .length(3, "CVV should be exactly be 3 digits")
    .required("Required")
}

export const updateCustomerSchema = {
    firstName: Yup.string()
        .min(2, "Must be 2 characters or more")
        .required("Required"),
    lastName: Yup.string()
        .min(2, "Must be 2 characters or more")
        .required("Required"),
    userName: Yup.string()
        .min(3, "Must be 3 characters or more")
        .required("Required"),
    phone: Yup.string()
        .length(10, "Phone number must be exactly 10 characters")
        .matches(/^\d+$/, "Phone number must only contain digits")
        .required("Required"),
    email: Yup.string().email("Invalid email address").required("Required"),
    
};

export const reserveCarSchema = {
    startDate: Yup.string()
    .required("Required"),
    endDate: Yup.string()
    .required("Required")
}