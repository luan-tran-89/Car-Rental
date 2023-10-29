import React, { useEffect, useState } from "react";
import { Box, Card, CardContent } from "@mui/material";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import { useNavigate } from "react-router-dom";
import { getCustomerById, getCustomerRentalHistory } from "../../Actions/UserAction";
import { useParams } from "react-router-dom";

import { getCar } from "../../Actions/CarAction";

export default function CustomerRentalHistory() {
  const navigate = useNavigate();
  const [customerRentalHistory, setCustomerRentalHistory] = useState([]);
  const { userId } = useParams();
  const [customerDetails, setCustomerDetails] = useState(null);


  useEffect(() => {
    const fetchData = async () => {
      try {
        const rentalHistory = await getCustomerRentalHistory(userId);
        if (rentalHistory.length === 0) {

          return;
        }

        const carDetailsPromises = rentalHistory.map(rental => fetchCarDetails(rental));
        const updatedRentalHistory = await Promise.all(carDetailsPromises);

        const userIdToFetch = updatedRentalHistory[0].userId;
        await fetchCustomerById(userIdToFetch);

        setCustomerRentalHistory(updatedRentalHistory);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, []);

  async function fetchCustomerById(userId) {
    try {
      const response = await getCustomerById(userId);
      setCustomerDetails(response);
    } catch (error) {
      console.error("Error fetching customer details:", error);
    }
  }

  async function fetchCarById(carId) {
    try {
      const response = await getCar(carId);
      return response;
    } catch (error) {
      console.error("Error fetching car details:", error);
    }
  }

  async function fetchCarDetails(rental) {
    try {
      rental.carDetails = await fetchCarById(rental.carId);
      return rental;
    } catch (error) {
      console.error("Error fetching car details for rental:", error);
      return rental;
    }
  }




  return (
    <Box >
      <Grid container spacing={2} alignItems="center" justifyContent="center">
        <Grid item xs={12} md={6}>
          <Box display="flex" justifyContent="space-between">
            <Typography
              sx={{ mt: 4, mb: 2 }}
              variant="h6"
              component="div"
              color={"red"}
            >
              {customerDetails ? `Rental History For ${customerDetails.firstName} | ${customerDetails.email}` : `No Rental History was found`}

            </Typography>
            <Typography
              sx={{ mt: 4, mb: 2, mr: 3, cursor: "pointer" }}
              color="error"
              onClick={() => navigate("/customer/rentalhistory")}
            >
              Back
            </Typography>
          </Box>
        </Grid>
      </Grid>
      <Grid
        display="flex"
        flexDirection="column"

        justifyContent="center"

      >


        {customerRentalHistory.length !== 0 ? (customerRentalHistory.map((rental, index) => (
          <Card key={index} sx={{ marginBottom: "1em", backgroundColor: "#FBD1A2" }}>

            <CardContent >
              <Grid container spacing={3}>

                <Grid item md={6} xs={12} >
                  <Typography color="error">Model: {rental.carDetails.model}  </Typography>
                </Grid>
                <Grid item md={6} xs={12}>
                  <Typography color="error">Make: {rental.carDetails.make} </Typography>
                </Grid>
                <Grid item md={6} xs={12} >
                  <Typography color="error">StartDate: {rental.startDate.slice(0, 10)}</Typography>
                </Grid>
                <Grid item md={6} xs={12} >
                  <Typography color="error">End Date: {rental.endDate.slice(0, 10)} </Typography>
                </Grid>
                <Grid item md={6} xs={12} >
                  <Typography color="error">Cost Per Day: {rental.carDetails.costPerDay}  </Typography>
                </Grid>

                <Grid item md={6} xs={12} >
                  <Typography color="error">Total CostPrice: {rental.totalCost} </Typography>
                </Grid>
              </Grid>
            </CardContent>
          </Card>
        ))) : null}


      </Grid>
    </Box>
  );
}
