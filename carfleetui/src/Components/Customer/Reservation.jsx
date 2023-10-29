import Container from "@mui/material/Container";
import { Grid, Button, Alert } from "@mui/material";
import ViewCar from "../ResuableComponents/ViewCar";
import { useEffect, useState } from "react";
import NotReserved from "./NotReserved";
import { getCustomerCarReservations } from "../../Actions/RentalAction";
import { getCar } from "../../Actions/CarAction";
import { useNavigate } from "react-router-dom";


export default function Reservation() {
  const [carReserved, setCarReserved] = useState([]);
  const [status, setStatus] = useState(false);
  const [showAlert, setShowAlert] = useState(false);


  const navigate = useNavigate();

  console.log(localStorage.getItem("userId"))

  useEffect(() => {
    getCustomerCarReservations(localStorage.getItem("userId")).then(res => {
      console.log(res)
      localStorage.setItem("partialReservation", JSON.stringify(res[0]))
      if (res.length !== 0) {
        getCar(res[0].carId).then(res => {

          setCarReserved(res)

        }).catch(error => {
          console.log(error)
          console.log("Unable to fetch the car details")
        })
      } else {
        console.log("I am here")
        setStatus(true)
      }
    }).catch(error => {
      console.log(error)
    })
  }, [localStorage.getItem("userId")])



  useEffect(() => {
    if (showAlert) {
      const timer = setTimeout(() => {
        setShowAlert(false);
      }, 4000);

      return () => clearTimeout(timer);
    }
  }, [showAlert]);

  const handleSubmit = (value) => {
    let returnCar = carReserved.filter((car) => car.make !== value);


    setCarReserved(returnCar);
  };




  // debugger;
  return (
    <>
      {showAlert && (
        <Container>
          <Alert severity="success" variant="filled">
            Awesome! Enjoy the ride
          </Alert>
        </Container>
      )}

      <Grid container="true" sx={{ padding: "1em" }} spacing={3}>
        {status ? (<Container>
          <Grid item md={12}>
            <NotReserved />
          </Grid>
        </Container>
        ) : (
          <>

            <Grid item md={3}>
              <ViewCar car={carReserved} medsize={12}>
                <Grid item md={6}>
                  <Button
                    variant="outlined"
                    onClick={() => handleSubmit("test")}
                  >
                    Return
                  </Button>
                </Grid>
                <Grid item md={6}>
                  <Button
                    variant="outlined"
                    onClick={() => {
                      navigate("/customer/payment")

                    }}
                  >
                    Pickup
                  </Button>
                </Grid>
              </ViewCar>
            </Grid>

          </>

        )}
      </Grid>
    </>
  );
}
