import React, { useEffect, useState } from "react";

import { Box, Stack, CircularProgress, Container, Alert } from "@mui/material";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import ListItemText from "@mui/material/ListItemText";
import Avatar from "@mui/material/Avatar";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import FolderIcon from "@mui/icons-material/Folder";
import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { getCustomer } from "../../Actions/UserAction";
import { getCustomerPaymentMethods } from "../../Actions/PaymentAction";



export default function Accounts() {
  const navigate = useNavigate();

  const [customerPaymentMethods, setCustomerPaymentMethods] = useState([]);
  const [showProgress, setShowProgress] = useState(false);
  const [showWarningAlert, setShowWarningAlert] = useState(false);

  useEffect(() => {
    getPaymentMethods();
  }, [])

  function getPaymentMethods() {
    // console.log(localStorage.getItem("userEmail"))

    getCustomerPaymentMethods(localStorage.getItem("userId")).then(res => {
      // console.log(res)
      // debugger
      if (res.length === 0) {
        setShowWarningAlert(true)
      } else {
        setShowProgress(true)
        setCustomerPaymentMethods(res)
        setShowProgress(false)
        setShowWarningAlert(false)
      }
      // debugger
      // console.log(res)

    }).catch(err => {
      console.log(err)
      // console.log("Error in fetching customers card")
    })
  }

  return (


    <Box sx={{ flexGrow: 1 }}>
      {showProgress ? <Container sx={{ marginTop: "1.5em" }}>
        <Stack sx={{ color: 'grey.500' }} spacing={2} direction="row">
          <CircularProgress color="secondary" />
          <CircularProgress color="success" />
          <CircularProgress color="inherit" />
        </Stack>
      </Container> :
        <Grid container spacing={2} alignItems="center" justifyContent="center">



          <Grid item xs={12} md={6}>
            <Box display="flex" justifyContent="space-between">
              <Typography
                sx={{ mt: 4, mb: 2 }}
                variant="h6"
                component="div"
                color={"red"}
              >
                Your Cards
              </Typography>
              <Typography
                sx={{ mt: 4, mb: 2, mr: 3, cursor: "pointer" }}
                color="error"
                onClick={() => navigate("add-card")}
              >
                Add Card
              </Typography>
            </Box>
            {showWarningAlert ? <Container sx={{ marginTop: "1.5em" }}>
              <Alert severity="warning" variant="filled">
                You don't have any card yet! No worries, add now :)
              </Alert>
            </Container> :
              (
                customerPaymentMethods.map((card, index) => (
                  <List key={index}>
                    <ListItem
                      secondaryAction={
                        <Button
                          variant="outlined"
                          color="error"
                          onClick={() => navigate(`update-payment/${card.methodId}`)}
                        >
                          Update
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
              )
            }
          </Grid>
        </Grid>
      }
    </Box>

  );
}
