import * as React from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import { styled, Grid, Typography } from "@mui/material";

const CustomGrid = styled(Grid)({
  justifyContent: "space-around",
});

export default function ViewCar(props) {

  const defaultImage = "https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8MTh8fHxlbnwwfHx8fHw%3D&auto=format&fit=crop&w=500&q=60";

  return (
    <Card sx={{ backgroundColor: "#FBD1A2" }}>
      <CardMedia
        component="img"
        height="120"
        image={props.car.image ? props.car.image : defaultImage}
        alt="green iguana"
      />
      <CardContent>
        <CustomGrid container={true} spacing={2}>
          <Grid item md={6}>
            <Typography color="error" component="span">
              Model:{" "}
            </Typography>
            {props.car.model}
          </Grid>

          <Grid item md={6}>
            <Typography color="error" component="span">
              Make:{" "}
            </Typography>
            {props.car.make}
          </Grid>
          <Grid item md={6}>
            <Typography color="error" component="span">
              Status:{" "}
            </Typography>
            {props.car.status}
          </Grid>
          <Grid item md={6}>
            <Typography color="error" component="span">
              Fixed Cost:{" "}
            </Typography>
            ${props.car.fixedCost}
          </Grid>
          <Grid item md={props.medsize}>
            <Typography color="error" component="span">
              Cost Per Day:{" "}
            </Typography>
            ${props.car.costPerDay}
          </Grid>
          {props.children}
          {/* {...children} */}
        </CustomGrid>
      </CardContent>
    </Card>
  );
}
