# Assignment Francine

The application consists of scanning a QR code and storing the key contained in it. From this stored key, the application will generate a token every 30 seconds and display it on the screen.


### Requirements for the project

- Have NDK installed
- Have CMte installed
- Have LLDB installed (if debug is used)


### Challenges faced

I separated the execution of the task into 3 parts: ¹scan the QR code, ²inclusion of the native library and ³OTP generation.

1- I researched the best way to implement QR code scanning and I had no problems implementing it.

2- The inclusion of the native library was a bit more complicated but it happened without problems. I had to research a little more on the subject to be able to implement it.

3- In the OTP generation I found some problems to update it every second. However, I increased the refresh time to 30 seconds and before unpacking the memory in the provided library, I checked if it was actually allocated to avoid mistakes.

#### Considerations:

The task was challenging because I had not worked with those resources yet. I worked hard to develop it in the best way possible and I was very satisfied with the result.


