import * as React from "react";

import { DemoContainer } from "@mui/x-date-pickers/internals/demo";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";

export default function DatePickerComponent() {
  const [value, setValue] = React.useState(null);
  console.log(value)

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DemoContainer components={["DatePicker"]}>
        <DatePicker value={value} onChange={(newValue) => setValue(newValue)} />
      </DemoContainer>
    </LocalizationProvider>
  );
}
