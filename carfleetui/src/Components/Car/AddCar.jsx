import React, { useState, useEffect } from "react";
import {
  Card,
  Grid,
  styled,
  CardContent,
  Button,
  Box,
  Container,
  Typography,
  TextField,
  Alert
} from "@mui/material";
import FileInput from "../ResuableComponents/InputFileUpload";
import { useFormik } from "formik";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import { addCar } from "../../Actions/CarAction";

const CustomGrid = styled(Grid)({
  justifyContent: "space-around",
});
const CustomBox = styled(Box)({
  padding: 2,

});


export const AddCar = (props) => {
  const [showSuccessAlert, setShowSuccessAlert] = useState(false);

  useEffect(() => {
    let timer = setTimeout(() => {
      setShowSuccessAlert(false)
    }, 6000);

    return () => clearTimeout(timer);
  }, [showSuccessAlert])

  const formik = useFormik({
    initialValues: {
      model: "",
      make: "",
      status: "",
      fixedCost: "",
      costPerDay: "",
    },
    onSubmit: (values, { resetForm }) => {
      // console.log(values);

      addCar(values).then(res => {
        // console.log(res)
        resetForm();
        setShowSuccessAlert(true)
      }).catch(err => {
        console.log(err)
        resetForm()
      })


    },
  });
  return (
    <CustomBox
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
    >
      {showSuccessAlert && <Container sx={{ marginTop: "1.5em" }}>
        <Alert severity="success" variant="filled">
          Nice! The Car was Added Succesfully:)
        </Alert>
      </Container>}
      <Typography
        variant="h4"
        color={"error"}
        align="center"
        sx={{ marginBottom: "1em" }}
      >
        New Car
      </Typography>

      <Card component={"form"} onSubmit={formik.handleSubmit} sx={{ backgroundColor: "#FBD1A2" }}>
        <CardContent>
          <CustomGrid container spacing={3}>
            <Grid item md={6} container justifyContent="center">
              <TextField
                required
                sx={{ width: "50%" }}
                // fullWidth
                id="model"
                label="Model"
                name="model"
                autoComplete="model"
                variant="standard"
                value={formik.values.model}
                onChange={formik.handleChange}
                color="error"
              />
            </Grid>

            <Grid item md={6} container justifyContent="center">
              <TextField
                required
                sx={{ width: "50%" }}
                // fullWidth
                id="make"
                label="Make"
                name="make"
                autoComplete="make"
                variant="standard"
                value={formik.values.make}
                onChange={formik.handleChange}
                color="error"
              />
            </Grid>
            <Grid item md={6} container justifyContent="center">
              <TextField
                required
                // fullWidth
                sx={{ width: "50%" }}
                id="fixedCost"
                label="Fixed Cost"
                name="fixedCost"
                autoComplete="fixedCost"
                type="number"
                variant="standard"
                value={formik.values.fixedCost}
                onChange={formik.handleChange}
                color="error"
              />
            </Grid>
            <Grid item md={6} container justifyContent="center">
              <TextField
                required
                sx={{ width: "50%" }}
                // fullWidth
                id="costPerDay"
                label="Cost Per Day"
                name="costPerDay"
                autoComplete="costPerDay"
                type="number"
                variant="standard"
                value={formik.values.costPerDay}
                onChange={formik.handleChange}
                color="error"
              />
            </Grid>
            <Grid item md={6} container justifyContent="center">
              <FormControl variant="standard" sx={{ width: "50%" }}>
                <InputLabel id="basecost-label">Car Status</InputLabel>
                <Select
                  labelId="carstatus-label"
                  id="carstatus"
                  name="status"
                  value={formik.values.status}
                  onChange={formik.handleChange}
                >
                  <MenuItem value={"AVAILABLE"}>Available</MenuItem>
                  <MenuItem value={"RESERVED"}>Reserved</MenuItem>
                  <MenuItem value={"PICKED"}>Picked</MenuItem>
                  <MenuItem value={"UNDER_MAINTENANCE"}>
                    Under-Maintenance
                  </MenuItem>
                  <MenuItem value={"DISABLED"}>Disable</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item md={6} container justifyContent="center">
              {/* <FileInput /> */}
            </Grid>
            <Grid item md={6} container justifyContent="center">
              <Button
                variant="outlined"
                // fullWidth
                // size="sma÷ll"
                type="submit"
                // onClick={handleClickOpen}
                color="error"
                sx={{ mt: 3, mb: 2, width: "25%" }}
              >
                Add Car
              </Button>
            </Grid>
          </CustomGrid>
        </CardContent>
      </Card>
    </CustomBox>
  );
};
