import React, { useState } from "react";
import { Box, Alert, Avatar, Typography, Container, Grid, TextField, Button } from "@mui/material";
import { useFormik } from "formik";
import { registerCustomer } from "../../Actions/UserAction";
import { useNavigate } from "react-router-dom";
import CarRentalIcon from "@mui/icons-material/CarRental";
import * as Yup from "yup";
import {
  CustomErrorDiv,
  signUpValidationSchema,
} from "../../Common/YupValidations";

export default function SignUp() {

  const navigate = useNavigate();
  const [showSuccessAlert, setShowSuccessAlert] = useState(false);
  const [showErrorAlert, setShowErrorAlert] = useState(false);

  const formik = useFormik({
    initialValues: {
      firstName: "",
      lastName: "",
      userName: "",
      phone: "",
      email: "",
      password: "",
      confirmpassword: "",
    },
    validationSchema: Yup.object(signUpValidationSchema),
    onSubmit: (values, { resetForm }) => {
      const { confirmpassword, ...dataToSend } = values;
      resetForm();
      registerAndHandleResponse(dataToSend)
    },
  });

  async function registerAndHandleResponse(dataToSend) {
    try {
      await registerCustomer(dataToSend);
      handleSuccessfulRegistration();
    } catch (error) {
      console.error(error);
      handleRegistrationError();
    }
  }

  function handleSuccessfulRegistration() {
    setShowSuccessAlert(true)

    const timerId = setTimeout(() => {
      setShowSuccessAlert(false);
      redirectFunction();
    }, 6000)

    setTimeout(() => {
      clearTimeout(timerId);
    }, 6000)
  }


  function handleRegistrationError() {
    setShowErrorAlert(true);
    const timerId = setTimeout(() => {
      setShowErrorAlert(false);

    }, 6000)

    setTimeout(() => {
      clearTimeout(timerId);
    }, 6000)

  }

  const redirectFunction = () => {
    navigate("/login")
  }

  return (
    <Container component="main" maxWidth="xs" >
      <>
        {showSuccessAlert && <Container sx={{ marginTop: "1.5em" }}>
          <Alert severity="success" variant="filled">
            Good News!! You Are In :) You'll Be Redirected To Login
          </Alert>
        </Container>}
      </>
      <>
        {showErrorAlert && <Container sx={{ marginTop: "1.5em" }}>
          <Alert severity="error" variant="filled">
            Oops!! Something Happend, Try Again :()
          </Alert>
        </Container>}
      </>
      <Box sx={{ flexGrow: 1 }}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <Box
              sx={{
                my: 2,
                mx: 2,
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
            >
              <Typography
                style={{ color: "#2196f3" }}
                component="h4"
                variant="h4"
              >
                SignUp For An Account
              </Typography>

              <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
                <CarRentalIcon />
              </Avatar>

              <Typography
                style={{ color: "#2196f3" }}
                component="h6"
                variant="h6"
              >
                Register
              </Typography>
            </Box>
          </Grid>
        </Grid>
      </Box>
      <Box
        sx={{
          marginTop: 4,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Box
          component="form"
          noValidate
          onSubmit={formik.handleSubmit}
          sx={{ mt: 3 }}
        >
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <TextField
                autoComplete="given-name"
                name="firstName"
                fullWidth
                id="firstName"
                label="First Name"
                autoFocus
                value={formik.values.firstName}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
              />
              {formik.touched.firstName && formik.errors.firstName ? (
                <CustomErrorDiv>{formik.errors.firstName}</CustomErrorDiv>
              ) : null}
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                id="lastName"
                label="Last Name"
                name="lastName"
                autoComplete="family-name"
                value={formik.values.lastName}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
              />
              {formik.touched.lastName && formik.errors.lastName ? (
                <CustomErrorDiv>{formik.errors.lastName}</CustomErrorDiv>
              ) : null}
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                id="userName"
                label="Username"
                name="userName"
                autoComplete="family-name"
                value={formik.values.userName}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
              />
              {formik.touched.userName && formik.errors.userName ? (
                <CustomErrorDiv>{formik.errors.userName}</CustomErrorDiv>
              ) : null}
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                id="phone"
                label="Phone"
                name="phone"
                autoComplete="family-name"
                value={formik.values.phone}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
              />
              {formik.touched.phone && formik.errors.phone ? (
                <CustomErrorDiv>{formik.errors.phone}</CustomErrorDiv>
              ) : null}
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                id="email"
                label="Email Address"
                name="email"
                autoComplete="email"
                value={formik.values.email}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
              />
              {formik.touched.email && formik.errors.email ? (
                <CustomErrorDiv>{formik.errors.email}</CustomErrorDiv>
              ) : null}
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="new-password"
                value={formik.values.password}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
              />
              {formik.touched.password && formik.errors.password ? (
                <CustomErrorDiv>{formik.errors.password}</CustomErrorDiv>
              ) : null}
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                name="confirmpassword"
                label="Confirm Password"
                type="password"
                id="confirmpassword"
                autoComplete="new-password"
                value={formik.values.confirmpassword}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
              />
              {formik.touched.confirmpassword &&
                formik.errors.confirmpassword ? (
                <CustomErrorDiv>
                  {formik.errors.confirmpassword}
                </CustomErrorDiv>
              ) : null}
            </Grid>
          </Grid>
          <Button
            type="submit"
            fullWidth
            variant="outlined"
            sx={{ mt: 3, mb: 2 }}
          >
            Register
          </Button>
          <Grid container justifyContent="flex-end">
            <Grid item>
              <Box variant="body2">
                <Typography color={"primary"} component={"span"}>Already have an account?</Typography>

                <Box component={"span"}>
                  {" "}
                  <Button onClick={() => navigate("/login")}> LogIn</Button>
                </Box>
              </Box>
            </Grid>
          </Grid>
        </Box>
      </Box>
    </Container>
  );
}
