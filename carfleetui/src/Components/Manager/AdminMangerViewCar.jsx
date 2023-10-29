import React, { useState, useEffect } from "react";

import { Grid, Button, Container, Alert } from "@mui/material";
import ViewCar from "../ResuableComponents/ViewCar";
import { useNavigate } from "react-router-dom";
import { getCars } from "../../Actions/CarAction"
import SearchComponent from "../ResuableComponents/SearchComponent";
import { useLoaderData } from "react-router-dom";
import ElectricCarIcon from "@mui/icons-material/ElectricCar";



export default function AdminMangerViewCar({ managerViewCar }) {
  const navigate = useNavigate();
  // debugger

  const [filteredCars, setFilteredCars] = useState([]);
  const [searchStatus, setSearchStatus] = useState(true);
  const [fetchCars, setFetchCars] = useState(false);

  useEffect(() => {
    getCars().then((res) => {
      // console.log(res);
      // debugger

      setFilteredCars(res)
    }).catch(err => {
      console.log("Something went wrong while fetching cars")
    })
  }, [searchStatus, fetchCars]);


  useEffect(() => {
    if (!searchStatus) {
      const timer = setTimeout(() => {
        setSearchStatus(true);
      }, 4000);
      // setFilteredCars(cars);

      return () => clearTimeout(timer);
    }
  }, [searchStatus]);


  const handleSearchResults = (searchQuery) => {
    console.log(typeof searchQuery)
    if (searchQuery) {
      const result = filteredCars.filter(
        (car) =>
          car.make.toLowerCase().includes(searchQuery.toLowerCase()) ||
          car.model.toLowerCase().includes(searchQuery.toLowerCase()) ||
          car.costPerDay === Number(searchQuery)
      );
      if (result.length === 0) {
        setSearchStatus(false);
      }
      setFilteredCars(result);
    } else if (searchQuery === '') {
      setFetchCars(true)
      setSearchStatus(true)
    }
  };

  //implement remove car api
  const removeCar = (carId) => {
    let cars = filteredCars.filter((car) => car.id !== carId);

    setFilteredCars(cars);
  };

  return (
    <>
      <SearchComponent
        onSearch={handleSearchResults}
        labelTag={"Search Car By Model | Make | Cost Per Day"}
        buttonTag={"All Cars"}
        buttonIcon={<ElectricCarIcon />}
      />
      {searchStatus ? (
        <Grid container="true" sx={{ padding: "1em" }} spacing={3}>
          {/* {<CircularProgress color="secondary" />} */}

          {filteredCars.length !== 0 ? (
            filteredCars.map((car, index) => (
              <Grid key={index} item md={4}>
                <ViewCar car={car} medsize={6}>
                  <Grid item md={6}>
                    {managerViewCar && <Button
                      onClick={() => navigate(`/car/update-car/${car.carId}`)}
                      variant="outlined"
                      color="error"
                    >
                      Update Car
                    </Button>}

                  </Grid>
                  <Grid item md={6}>
                    <Button
                      onClick={() => navigate(`/car/rental-history/${car.carId}`)}
                      variant="outlined"
                      color="error"
                    >
                      Rental History
                    </Button>
                  </Grid>
                  <Grid item md={6}>
                    <Button onClick={() => removeCar(car.carId)} variant="outlined" color="error">
                      Remove Car
                    </Button>
                  </Grid>

                  <Grid item md={12}>
                    <Button
                      onClick={() => navigate(`/manager/cars/maintenanace/${car.carId}`)}
                      variant="outlined"
                      color="error"
                    >
                      Maintainance History
                    </Button>
                  </Grid>
                </ViewCar>
              </Grid>
            ))
          ) : <h2>No cars to display</h2>}

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


