import React, { useEffect, useState } from "react";
import { Box, Card, CardContent } from "@mui/material";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import { useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";
import { getCarMaintenanceHistory, getCarRentalHistory } from "../../Actions/CarAction";
import { getCustomer } from "../../Actions/UserAction";

export default function CarRentalHistory() {
  const navigate = useNavigate();
  const [carRentalHistory, setCarRentalHistory] = useState([]);

  const { carId } = useParams();

  // console.log(carId)

  useEffect(() => {
    fetchCarRentalHistory()
  }, [])

  function fetchCarRentalHistory() {
    getCarRentalHistory(carId).then(res => {
      console.log(res[0].userId)

      setCarRentalHistory(res)
    }).catch(error => {
      console.log(error)
    })
  }

  // function fetchUser() {
  //   getCustomer()
  // }


  return (
    <Box sx={{ flexGrow: 1 }}>
      <Grid container spacing={2} alignItems="center" justifyContent="center">
        <Grid item xs={12} md={6}>
          <Box display="flex" justifyContent="space-between">
            <Typography
              sx={{ mt: 4, mb: 2 }}
              variant="h6"
              component="div"
              color={"red"}
            >
              Rental History
            </Typography>
            <Typography
              sx={{ mt: 4, mb: 2, mr: 3, cursor: "pointer" }}
              color="error"
              onClick={() => navigate("/manager/cars")}
            >
              Back
            </Typography>
          </Box>
        </Grid>
      </Grid>
      <Grid
        display="flex"
        flexDirection="column"
        alignItems="center"
        justifyContent="center"
        sx={{ marginTop: "1em" }}
      >
        {carRentalHistory.length !== 0 ? (carRentalHistory.map((car, index) => (
          <Card key={index} sx={{ marginBottom: "1em" }}>
            <CardContent>
              <Grid container spacing={3}>

                <Grid item md={6} xs={12} justifyContent="center">
                  <Typography color="error">Start Date: {car.startDate}</Typography>
                </Grid>
                <Grid item md={6} xs={12} justifyContent="center">
                  <Typography color="error">End Date: {car.endDate}</Typography>
                </Grid>
                <Grid item md={6} xs={12} justifyContent="center">
                  <Typography color="error">Total Cost: {car.totalCost}</Typography>
                </Grid>
              </Grid>
            </CardContent>
          </Card>
        ))) : <h2>This car doesn't have rental history</h2>}



      </Grid>
    </Box>
  );
}
