import React, { useEffect, useState } from "react";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import { Box, Typography, Alert } from "@mui/material";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { useFormik } from "formik";
import { getCustomerPaymentMethod, updatePaymentMethod } from "../../Actions/PaymentAction";
import { useParams } from "react-router-dom";
import { CustomErrorDiv, addCardValidationSchema } from "../../Common/YupValidations";
import * as Yup from "yup";

const defaultTheme = createTheme();

export default function UpdatePayment() {

  const { cardId } = useParams();

  const [cardType, setCardType] = useState(null)

  const [showSuccessAlert, setShowSuccessAlert] = useState(false);

  useEffect(() => {
    getCustomerPaymentMethod(cardId).then(res => {
      // console.log(res)
      setCardType(res.cardType)
      formik.setValues({
        cardNumber: res.cardNumber || "",
        expiryDate: res.expiryDate || "",

        cardType: res.cardType || "",

        cvv: res.cvv || ""
      });
    }).catch(error => {
      console.log("Unable to fetch user details")
      console.log(error)

    })
  }, [cardId])

  const formik = useFormik({
    initialValues: {
      cardNumber: "",

      expiryDate: "",
      cardType: "",
      cvv: ""
    },
    validationSchema: Yup.object(addCardValidationSchema),
    onSubmit: (values) => {
      console.log(values)
      values.cardType = cardType;

      updatePaymentMethod(cardId, values).then(res => {
        // console.log(res)
        setShowSuccessAlert(true)
      }).catch(error => {
        console.log(error)
      })
      // console.log(values)
    }
  });

  useEffect(() => {
    let timer = setTimeout(() => {
      setShowSuccessAlert(false)
    }, 6000);

    return () => clearTimeout(timer);
  }, [showSuccessAlert])

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        {/* <CssBaseline /> */}
        {showSuccessAlert && <Container sx={{ marginTop: "1.5em" }}>
          <Alert severity="success" variant="filled">
            Great!! The Card Was Updated Succefully
          </Alert>
        </Container>}
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
            <Typography variant="h6" color="secondary" sx={{ mb: 3 }}>
              Update Card Details
            </Typography>
            <Grid container spacing={3}>
              <Grid item xs={12} md={6}>
                <TextField
                  required
                  fullWidth
                  id="cardNumber"
                  label="Card Number"
                  name="cardNumber"
                  autoComplete="cardNumber"
                  variant="standard"
                  value={formik.values.cardNumber}
                  onChange={formik.handleChange}
                  onBlur={formik.handleBlur}
                  color="error"

                />
                {formik.touched.cardNumber && formik.errors.cardNumber ? (
                  <CustomErrorDiv>{formik.errors.cardNumber}</CustomErrorDiv>
                ) : null}
              </Grid>

              <Grid item xs={12} md={6}>
                <TextField
                  required
                  fullWidth
                  name="expiryDate"
                  label="Expiration Date"
                  type="text"
                  id="expiryDate"
                  autoComplete="expiryDate"
                  variant="standard"
                  value={formik.values.expiryDate}
                  onChange={formik.handleChange}
                  onBlur={formik.handleBlur}
                  color="error"
                />
                {formik.touched.expiryDate && formik.errors.expiryDate ? (
                  <CustomErrorDiv>{formik.errors.expiryDate}</CustomErrorDiv>
                ) : null}
              </Grid>

              <Grid item xs={12} md={6}>
                <TextField
                  required
                  fullWidth
                  name="cvv"
                  label="CVV"
                  type="text"
                  id="cvv"
                  autoComplete="cvv"
                  variant="standard"
                  value={formik.values.cvv}
                  onChange={formik.handleChange}
                  onBlur={formik.handleBlur}
                  color="error"
                />
                {formik.touched.cvv && formik.errors.cvv ? (
                  <CustomErrorDiv>{formik.errors.cvv}</CustomErrorDiv>
                ) : null}
              </Grid>
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="outlined"
              color="error"
              sx={{ mt: 3, mb: 2 }}
            >
              Update
            </Button>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
