import * as React from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import { useNavigate } from "react-router-dom";
import { Button, CardActionArea, CardActions, Box } from "@mui/material";

export default function NotReserved() {
  const navigate = useNavigate();
  return (
    <Box>
      <Card sx={{}}>
        <CardActionArea>
          <CardContent>
            <Typography
              gutterBottom
              variant="h5"
              component="div"
              color={"error"}
              //   align="center"
            >
              For now, you haven't reserved a car yet, but you can change it
              now:)
            </Typography>
          </CardContent>
        </CardActionArea>
        <CardActions>
          <Button
            size="medium"
            color="success"
            onClick={() => navigate("/customer/cars")}
          >
            Reserve
          </Button>
        </CardActions>
      </Card>
    </Box>
  );
}
