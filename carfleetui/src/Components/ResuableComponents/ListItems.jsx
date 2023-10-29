import * as React from "react";
import { Box, Grid, Typography, styled, Paper, Divider } from "@mui/material";

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#fff",
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: "center",
  color: theme.palette.text.secondary,
}));

const CustomBox = styled(Box)({
  padding: 2,
});

export default function ListItems({
  model = "Caravan",
  make = "Porche",
  startDate = "12/12/2023",
  endDate = "12/01/2024",
  cost = 50,
  customerName = "Kevin",
}) {
  return (
    <CustomBox sx={{ flexGrow: 1 }}>
      <Grid
        container="true"
        spacing={3}
        sx={{ marginBottom: "1em", marginTop: "0.5em" }}
      >
        <Grid item xs={12} md={2}>
          <Item>
            <Typography variant="h6" color="primary">
              Model: {model}
            </Typography>
          </Item>
        </Grid>
        <Grid item xs={12} md={2}>
          <Item>
            <Typography variant="h6" color="primary">
              Make: {make}
            </Typography>
          </Item>
        </Grid>
        <Grid item xs={12} md={2}>
          <Item>
            <Typography variant="h6" color="primary">
              Customer Name: {customerName}
            </Typography>
          </Item>
        </Grid>
        <Grid item xs={12} md={2}>
          <Item>
            <Typography variant="h6" color="primary">
              Start Date: {startDate}
            </Typography>
          </Item>
        </Grid>
        <Grid item xs={12} md={2}>
          <Item>
            <Typography variant="h6" color="primary">
              End Date: {endDate}
            </Typography>
          </Item>
        </Grid>
        <Grid item xs={12} md={2}>
          <Item>
            <Typography variant="h6" color="primary">
              Cost: {cost}
            </Typography>
          </Item>
        </Grid>
      </Grid>
      <Divider orientation="horizontal" flexItem />
    </CustomBox>
  );
}
