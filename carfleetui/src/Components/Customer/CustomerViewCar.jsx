import React, { useState, useEffect } from "react";
import ViewCar from "../ResuableComponents/ViewCar";
import { Grid, Button, Container, Alert } from "@mui/material";
import SearchComponent from "../ResuableComponents/SearchComponent";
import ElectricCarIcon from "@mui/icons-material/ElectricCar";
import { useLocation, useNavigate } from "react-router-dom";
import { getCars } from "../../Actions/CarAction";



export default function CustomerViewCar() {


  const [filteredCars, setFilteredCars] = useState([]);
  const location = useLocation();
  const [showAlert, setShowAlert] = useState(false);
  const [searchStatus, setSearchStatus] = useState(true);
  const navigate = useNavigate();
  const [disableButton, setDisableButton] = useState(false)
  const [showAllCars, setShowAllCars] = useState(false)

  useEffect(() => {

    getCars().then((res) => {
      // console.log(res);
      let availableCars = res.filter(car => car.status === 'AVAILABLE');
      setFilteredCars(availableCars)
      // setFilteredCars(res)

    }).catch(err => {
      console.log("Something went wrong while fetching cars")
    })
  }, [searchStatus, showAllCars]);

  useEffect(() => {
    if (showAlert) {
      const timer = setTimeout(() => {
        setShowAlert(false);
      }, 4000);

      return () => clearTimeout(timer);
    }
  }, [showAlert]);

  useEffect(() => {
    if (!searchStatus) {
      const timer = setTimeout(() => {
        setSearchStatus(true);
      }, 4000);
      // setFilteredCars(carInfo);

      return () => clearTimeout(timer);
    }
  }, [searchStatus]);

  const handleSearchResults = (searchQuery) => {
    // console.log(searchQuery)
    if (searchQuery) {
      const result = filteredCars.filter(
        (car) =>
          car.make.toLowerCase().includes(searchQuery.toLowerCase()) ||
          car.model.toLowerCase().includes(searchQuery.toLowerCase()) ||
          car.costPerDay === Number(searchQuery)
      );
      // console.log(result);
      if (result.length === 0) {
        setSearchStatus(false);
      }
      setFilteredCars(result);
    } else {
      setShowAllCars(true)
    }

  };

  const handleReserveButton = (carId) => {

    if (location.pathname === '/') {
      setDisableButton(true)
      setShowAlert(true)
    } else {
      navigate(`/customer/reservecar/${carId}`);
    }
  }


  return (
    <>
      {showAlert && (
        <Container sx={{ marginTop: "1.5em" }}>
          <Alert severity="warning" variant="filled">
            SignIn Or SignUp To Reserve A Car
          </Alert>
        </Container>
      )}
      <SearchComponent
        onSearch={handleSearchResults}
        labelTag={"Search Car By Model | Make | Cost Per Day"}
        buttonTag={"All Cars"}
        buttonIcon={<ElectricCarIcon />}
      />{" "}
      {searchStatus ? (
        <Grid container={true} sx={{ padding: "1em" }} spacing={3}>
          {filteredCars.length !== 0 ? (
            filteredCars.map((car, key) => (
              <Grid key={key} item md={3}>
                <ViewCar car={car} medsize={12}>
                  <Grid item md={12}>
                    <Button
                      disabled={disableButton ? true : false}
                      variant="outlined"
                      onClick={() => handleReserveButton(car.carId)}
                    >
                      Reserve
                    </Button>
                  </Grid>

                </ViewCar>
              </Grid>
            ))
          ) : <Container sx={{ marginTop: "1.5em" }}>
            <Alert severity="error" variant="filled">
              Sorry! No car available for Reserving or Booking
            </Alert>
          </Container>}

        </Grid>
      ) : (
        <Container sx={{ marginTop: "1.5em" }}>
          <Alert severity="warning" variant="filled">
            Seems You Have A Typo In Your Search :)
          </Alert>
        </Container>
      )}
    </>
  );
}

