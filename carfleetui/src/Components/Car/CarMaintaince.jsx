import React, { useEffect, useState } from "react";
import { Box, Card, CardContent } from "@mui/material";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import { useNavigate } from "react-router-dom";
import { getCarMaintenanceHistory } from "../../Actions/CarAction";
import { useParams } from "react-router-dom";

export default function CarMaintaince() {

  const navigate = useNavigate();

  const [carMaintenanceHistory, setCarMaintenanceHistory] = useState([]);

  let { carId } = useParams();
  // console.log(carId)

  useEffect(() => {
    getCarMaintenanceHistory(carId).then(res => {
      // console.log(res)
      setCarMaintenanceHistory(res)
    }).catch(err => {
      console.log("Unable to fetch car maintenance history")
    })
  }, [])

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
              Maintainance History -- Ferrari
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
        {carMaintenanceHistory.map((history) => (
          <Card key={history.id} sx={{ marginBottom: "1em" }}>
            <CardContent>
              <Grid container spacing={3}>
                <Grid item md={6} xs={12} justifyContent="center">
                  <Typography color="error">Description: {history.description}</Typography>
                </Grid>

                <Grid item md={6} xs={12} justifyContent="center">
                  <Typography color="error">Status: {history.status}</Typography>
                </Grid>
                <Grid item md={6} xs={12} justifyContent="center">
                  <Typography color="error">Start Date: {history.startDate}</Typography>
                </Grid>
                <Grid item md={6} xs={12} justifyContent="center">
                  <Typography color="error">End Date: {history.endDate}</Typography>
                </Grid>

              </Grid>
            </CardContent>
          </Card>
        ))}

      </Grid>
    </Box>
  );
}
