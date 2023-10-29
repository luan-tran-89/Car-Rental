import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import { Box, Typography, Grid, Button, TextField, Alert } from "@mui/material";
import DatePickerComponent from "../ResuableComponents/DatePickerComponent";
import { useFormik } from "formik";
import { customerReserveCar } from "../../Actions/RentalAction";
import { useParams } from "react-router-dom";
import { CustomErrorDiv, reserveCarSchema } from "../../Common/YupValidations";
import * as Yup from "yup";
import { useState, useEffect } from "react";

const defaultTheme = createTheme();

export default function ReserveACar() {

    const { carId } = useParams();
    const [successReservation, setSuccessReservation] = useState(false);
    const [alreadyReserved, setAlreadyReserved] = useState(false)

    const formik = useFormik({
        initialValues: {
            startDate: "",
            endDate: ""
        },
        validationSchema: Yup.object(reserveCarSchema),
        onSubmit: (values, { resetForm }) => {
            console.log(values)

            const newValues = {
                carId: carId,
                userId: localStorage.getItem("userId"),
                startDate: formatDate(values.startDate) + "",
                endDate: formatDate(values.endDate) + "",
            }

            console.log(newValues);

            customerReserveCar(newValues).then(res => {
                console.log(res)
                console.log("Success")
                setSuccessReservation(true)
            }).catch(error => {
                setAlreadyReserved(true)
                console.log(error)
            })
            resetForm()
        }
    })

    const formatDate = (dateStr) => {
        const parts = dateStr.split('-');
        return [parts[1], parts[2], parts[0]].join('/');
    };

    useEffect(() => {
        let timer = setTimeout(() => {
            setSuccessReservation(false)
        }, 6000);

        return () => clearTimeout(timer);
    }, [successReservation])




    return (
        <ThemeProvider theme={defaultTheme}>
            <Container component="main" maxWidth="xs">
                {/* <CssBaseline /> */}
                {
                    alreadyReserved && <Container sx={{ marginTop: "1.5em" }}>
                        <Alert severity="error" variant="filled">
                            You already have an active reservation. You cannot reserve another car until the current reservation is completed or cancelled.
                        </Alert>
                    </Container>
                }
                {
                    successReservation && <Container sx={{ marginTop: "1.5em" }}>
                        <Alert severity="success" variant="filled">
                            Sweet, nice taste:) Reserved successfully!
                        </Alert>
                    </Container>
                }
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
                        <Typography variant="h5" color="secondary">
                            Reservation
                        </Typography>

                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <Typography variant="h6">Start Date</Typography>
                                {/* <DatePickerComponent /> */}
                                <TextField
                                    autoComplete="startDate"
                                    name="startDate"
                                    type="date"
                                    required
                                    fullWidth
                                    id="startDate"
                                    // label="First Name"
                                    autoFocus
                                    value={formik.values.startDate}
                                    onChange={formik.handleChange}
                                    onBlur={formik.handleBlur}
                                />
                                {formik.touched.startDate && formik.errors.startDate ? (
                                    <CustomErrorDiv>{formik.errors.startDate}</CustomErrorDiv>
                                ) : null}
                            </Grid>
                            <Grid item xs={12}>
                                <Typography variant="h6">End Date</Typography>
                                <TextField
                                    autoComplete="endDate"
                                    name="endDate"
                                    type="date"
                                    required
                                    fullWidth
                                    id="endDate"
                                    // label="First Name"
                                    autoFocus
                                    value={formik.values.endDate}
                                    onChange={formik.handleChange}
                                    onBlur={formik.handleBlur}
                                />
                                {formik.touched.endDate && formik.errors.endDate ? (
                                    <CustomErrorDiv>{formik.errors.endDate}</CustomErrorDiv>
                                ) : null}
                            </Grid>
                        </Grid>
                        <Button type="submit" variant="outlined" sx={{ mt: 3, mb: 2 }}>
                            Reserve
                        </Button>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}