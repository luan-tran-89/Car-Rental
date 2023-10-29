import { useEffect, useState } from "react";
import { CustomerCard } from "./CustomerCard";
import { Grid, Container, Alert } from "@mui/material";
import { getCustomers } from "../../Actions/UserAction";
import PeopleAltIcon from "@mui/icons-material/PeopleAlt";
import SearchComponent from "../ResuableComponents/SearchComponent";




export default function ViewCustomers(props) {
  // console.log(props);

  const [customers, setCustomers] = useState([]);
  const [searchStatus, setSearchStatus] = useState(true);
  const [fetchCustomers, setFetchCustomers] = useState(false);

  useEffect(() => {
    getCustomers()
      .then((res) => {
        // console.log(res);
        setCustomers(res);
      })
      .catch((err) => {
        // console.log(err);
        console.log("Cannot fetch customers. Check again")
      });
  }, [searchStatus, fetchCustomers]);


  useEffect(() => {
    if (!searchStatus) {
      const timer = setTimeout(() => {
        setSearchStatus(true);
      }, 4000);
      // setCustomers(userInfo);

      return () => clearTimeout(timer);
    }
  }, [searchStatus]);

  const handleSearchResults = (searchQuery) => {
    if (searchQuery) {
      const result = customers.filter((user) =>
        user.email.toLowerCase().includes(searchQuery.toLowerCase())
      );
      // console.log(result);

      if (result.length === 0) {
        setSearchStatus(false);
      }
      setCustomers(result);
    } else {
      setFetchCustomers(true)
    }
  };
  // debugger;
  return (
    <>
      <SearchComponent
        onSearch={handleSearchResults}
        labelTag={"Search Customer By Email"}
        buttonTag={"All Customers"}
        buttonIcon={<PeopleAltIcon />}
      />
      {searchStatus ? (
        <Grid container="true" sx={{ padding: "1em" }} spacing={3}>
          {customers.map((customer) => (
            <Grid key={customer.userId} item md={4}>
              <CustomerCard user={customer} view={props.view}></CustomerCard>
            </Grid>
          ))}
        </Grid>
      ) : (
        <Container sx={{ marginTop: "1.5em" }}>
          <Alert severity="warning" variant="filled">
            Please Check The Email And Try Again :)
          </Alert>
        </Container>
      )}
    </>
  );
}
