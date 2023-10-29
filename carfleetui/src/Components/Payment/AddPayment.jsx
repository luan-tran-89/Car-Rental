import React, { useEffect, useState } from "react";
import { Button, Alert } from "@mui/material";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import {
  Box,
  FormControl,
  Typography,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { useFormik } from "formik";
import * as Yup from "yup";
import { CustomErrorDiv, addCardValidationSchema } from "../../Common/YupValidations";
import { addCard } from "../../Actions/CardAction";
import { addPaymentMethod } from "../../Actions/PaymentAction";

const defaultTheme = createTheme();

export default function AddPayment() {
  const [showSuccessAlert, setShowSuccessAlert] = useState(false);

  useEffect(() => {
    let timer = setTimeout(() => {
      setShowSuccessAlert(false)
    }, 6000);

    return () => clearTimeout(timer);
  }, [showSuccessAlert])
  const formik = useFormik({
    initialValues: {
      cardNumber: "",

      expiryDate: "",
      cardType: "",
      cvv: ""
    },
    validationSchema: Yup.object(addCardValidationSchema),
    onSubmit: (values, { resetForm }) => {
      // console.log(values);
      values.approvalAmount = 30000.0;
      values.userId = localStorage.getItem("userId")

      console.log(values)

      addPaymentMethod(values).then(res => {
        // console.log(res)
        resetForm()
        setShowSuccessAlert(true)
      }).catch(err => {
        resetForm()
        // console.log(err)
      })
      resetForm();
    }
  })


  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        {/* <CssBaseline /> */}
        {showSuccessAlert && <Container sx={{ marginTop: "1.5em" }}>
          <Alert severity="success" variant="filled">
            Sweet! New Card Added Succesfully:)
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
            <Typography variant="h6" color="error" sx={{ mb: 3 }}>
              New Card
            </Typography>
            <Grid container spacing={2}>
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
              <Grid item xs={12} md={6}>
                <SelectCardType cardType={formik.values.cardType} setCardType={formik.setFieldValue} />
              </Grid>
            </Grid>
            <Button
              type="submit"
              fullWidth
              variant="outlined"
              color="error"
              sx={{ mt: 3, mb: 2 }}
            >
              ADD CARD
            </Button>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}

const SelectCardType = ({ cardType, setCardType }) => {

  const handleChange = (event) => {
    setCardType("cardType", event.target.value);
  };

  return (
    <FormControl sx={{ m: 1, minWidth: 190 }} size="small">
      <InputLabel id="demo-select-small-label">Card Type</InputLabel>
      <Select
        labelId="demo-select-small-label"
        id="demo-select-small"
        value={cardType}
        label="cardtype"
        onChange={handleChange}
      >
        <MenuItem value={"VISA"}>Visa</MenuItem>
        <MenuItem value={"MASTER_CARD"}>Master</MenuItem>
      </Select>
    </FormControl>
  );
};
