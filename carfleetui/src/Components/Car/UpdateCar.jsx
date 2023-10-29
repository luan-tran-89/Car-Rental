import React, { useEffect, useState } from "react";
import {
  Card,
  Grid,
  styled,
  CardContent,
  Button,
  Box,
  Typography,
  TextField,
  Container,
  Alert
} from "@mui/material";
import FileInput from "../ResuableComponents/InputFileUpload";
import { useFormik } from "formik";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import { Navigate, useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";
import { getCar, updateCar } from "../../Actions/CarAction";

const CustomGrid = styled(Grid)({
  justifyContent: "space-around",
});
const CustomBox = styled(Box)({
  padding: 2,
  // border: "2px blue groove",
});


export const UpdateCar = (props) => {
  const navigate = useNavigate();
  const [successUpdateCar, setSuccessUpdateCar] = useState(false);

  const { carId } = useParams();


  useEffect(() => {

    getCar(carId)
      .then((res) => {

        formik.setValues({
          model: res.model || "",
          make: res.make || "",
          status: res.status || "",
          fixedCost: res.fixedCost || "",
          costPerDay: res.costPerDay || "",

        });

      })
      .catch((error) => {
        console.log(error);
      });
  }, [carId]);

  useEffect(() => {
    let timer = setTimeout(() => {
      setSuccessUpdateCar(false)
      navigate("/manager/cars")
    }, 4000);

    return () => clearTimeout(timer);
  }, [successUpdateCar])


  const formik = useFormik({
    initialValues: {
      model: "",
      make: "",
      status: "",
      fixedCost: "",
      costPerDay: "",
    },
    onSubmit: (values, { resetForm }) => {


      updateCar(carId, values).then(res => {

        setSuccessUpdateCar(true)
        resetForm();

      }).catch(error => {
        console.log(error)
      })
      resetForm();
    },
  });
  return (
    <CustomBox
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      sx={{ marginTop: "1em" }}
    >
      {successUpdateCar && <Container sx={{ marginTop: "1.5em" }}>
        <Alert severity="success" variant="filled">
          Perfect! The Car Was Updated Succesfully :)
        </Alert>
      </Container>}
      <Typography
        variant="h5"
        color={"secondary"}
        align="center"
        sx={{ marginBottom: "1em" }}
      >
        Update Car
      </Typography>

      <Card component={"form"} onSubmit={formik.handleSubmit}>
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
                  <MenuItem value={"DISABLED"}>Disabled</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item md={6} container justifyContent="center">
              <FileInput />
            </Grid>
            <Grid item md={6} container justifyContent="center">
              <Button
                variant="outlined"
                // fullWidth
                // size="sma÷ll"
                type="submit"
                // onClick={() => navigate("/manager/cars")}
                sx={{ mt: 3, mb: 2, width: "25%" }}
              >
                Update Car
              </Button>
            </Grid>
          </CustomGrid>
        </CardContent>
      </Card>
    </CustomBox>
  );
};
