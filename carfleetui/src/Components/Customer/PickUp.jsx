import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { Box, Typography, Grid, Button } from "@mui/material";
import DatePickerComponent from "../ResuableComponents/DatePickerComponent";

const defaultTheme = createTheme();

export default function PickUp() {
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      email: data.get("email"),
      password: data.get("password"),
    });
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <Box
          sx={{
            marginTop: 4,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Box
            component="form"
            noValidate
            onSubmit={handleSubmit}
            sx={{ mt: 3 }}
          >
            <Typography variant="h5" color="secondary">
              Pick Up Date
            </Typography>

            <Grid container spacing={2}>
              <Grid item xs={12}>
                <DatePickerComponent />
              </Grid>
            </Grid>
            <Button type="submit" variant="outlined" sx={{ mt: 3, mb: 2 }}>
              Confirm
            </Button>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
