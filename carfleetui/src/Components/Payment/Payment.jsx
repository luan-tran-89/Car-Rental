import React, { useState, useEffect } from "react";
import { getCustomerPaymentMethods } from "../../Actions/PaymentAction";
import Avatar from "@mui/material/Avatar";
import FolderIcon from "@mui/icons-material/Folder";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import ListItemText from "@mui/material/ListItemText";
import { Box, Grid, List, ListItem, Button, Alert, Typography, Container } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { addCustomerRental } from "../../Actions/RentalAction";

const Payment = (props) => {

    const [customerPaymentMethods, setCustomerPaymentMethods] = useState([]);

    const [showSuccessAlert, setShowSuccessAlert] = useState(false);
    const [noCard, setNoCard] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        getPaymentMethods();
    }, [])

    function getPaymentMethods() {
        // console.log(localStorage.getItem("userEmail"))

        getCustomerPaymentMethods(localStorage.getItem("userId")).then(res => {
            // console.log(res)
            // debugger
            if (res.length === 0) {
                setNoCard(true)
            } else {

                setCustomerPaymentMethods(res)
            }
            // debugger
            console.log(res)

        }).catch(err => {
            console.log(err)
            // console.log("Error in fetching customers card")
        })
    }

    const customerRentals = (methodId) => {
        // carId --> res[0].carId, res[0].userId, res[0].startDate, res[0].endDate

        const data = JSON.parse(localStorage.getItem("partialReservation"));
        delete data.totalCost;
        delete data.rentalId;
        data.paymentId = methodId;


        addCustomerRental(data).then(res => {
            console.log(res)
            setShowSuccessAlert(true)
        }).catch(error => {
            console.log(error)
        })
    }

    useEffect(() => {
        let timer = setTimeout(() => {
            setShowSuccessAlert(false)
            // navigate("/customer/cars÷")
        }, 5000);

        return () => clearTimeout(timer);
    }, [showSuccessAlert])

    return (


        <Box sx={{ flexGrow: 1 }}>
            {noCard && <Container sx={{ marginTop: "1.5em" }}>
                <Alert severity="error" variant="filled">
                    You Dont Have Card(s) yet. Add One:)
                </Alert>
            </Container>}
            {showSuccessAlert && <Container sx={{ marginTop: "1.5em" }}>
                <Alert severity="success" variant="filled">
                    Sweet! Enjoy The Ride
                </Alert>
            </Container>}
            <Grid container spacing={2} alignItems="center" justifyContent="center">
                <Grid item xs={12} md={6}>
                    <Box display="flex" justifyContent="space-between">
                        <Typography
                            sx={{ mt: 4, mb: 2 }}
                            variant="h6"
                            component="div"
                            color={"red"}
                        >
                            Choose The Card To Pay With
                        </Typography>
                        <Button onClick={() => navigate("/customer/accounts/add-card")} color="error">Add Card</Button>

                    </Box>
                    {

                        customerPaymentMethods.map((card, index) => (
                            <List key={index}>
                                <ListItem
                                    secondaryAction={
                                        <Button
                                            variant="outlined"
                                            color="error"
                                            onClick={() => customerRentals(card.methodId)}
                                        >
                                            Pay
                                        </Button>
                                    }
                                >
                                    <ListItemAvatar>
                                        <Avatar>
                                            <FolderIcon />
                                        </Avatar>
                                    </ListItemAvatar>
                                    <ListItemText
                                        primary={
                                            <React.Fragment>
                                                <Typography
                                                    // component="span"
                                                    variant="body2"

                                                    color="error"
                                                >
                                                    Card No: {card.cardNumber}
                                                </Typography>
                                                <Typography
                                                    // component="span"
                                                    variant="body2"
                                                    color="error"
                                                >
                                                    Name: {card.cardHolderName}
                                                </Typography>
                                                <Typography
                                                    // component="span"
                                                    variant="body2"
                                                    color="error"
                                                >
                                                    CVV: {card.cvv}
                                                </Typography>
                                                <Typography
                                                    // component="span"
                                                    variant="body2"
                                                    color="error"
                                                >
                                                    Expiring Date: {card.expirationDate}
                                                </Typography>
                                            </React.Fragment>
                                        }
                                    />
                                </ListItem>
                            </List>
                        ))

                    }
                </Grid>
            </Grid>

        </Box>

    );


}

export default Payment