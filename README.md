# Assignment Francine

The application consists of scanning a QR code and storing the key contained on it. From this stored key, the application will create a token every 30 seconds and display it on the screen.


### Requirements for the project

- Have NDK installed
- Have CMte installed
- Have LLDB installed (if debug is used)


### Challenges faced

I separated the execution of the task into 3 parts: ¹scan the QR code, ²inclusion of the native library and ³OTP generation.

1- I researched the best way to implement QR code scanning and I did not had any problem implementing it.

2- The inclusion of the native library was a bit more complicated but it happened without problems. I had to research a little more on the subject to be able to implement it.

3- In the OTP generation I found some problems to update it every second. However, I increased the refresh time to 30 seconds and before unpacking the memory in the provided library, I checked if it was actually allocated to avoid mistakes.

#### Considerations:

The task was challenging because I have not worked with those resources yet. I worked hard to develop it in the best way possible and I was very satisfied with the result.

----------------------------------------------------------------------------------------------------------------------------------------

### Code review

The functionality was not implemented I had really forgotten. But this implementation was easy.

Te timer implementation was replaced bu "Alarm Manager", which was able to do the same but cleaner. With this implementation, was possible remove callback methods from Android's lifecycle.

In the OTP generation, one step really was unnoticed. This implementation was not difficult and I was able to fix the variables, keeping them within the functions and always checking if they were setted before. Correcting the permission of access to camera, telling its need to the user, it was easy too.

#### Conclusion

Int the fixes' implementation I did not find any difficlties. I had a brief contact with the use of "Alarm Manager" and I already knew a bit about its functionality. And the others corrections werer only code optimization.

My biggest challenge and difficulty was about safety techniques. I did not find much information on the subject and I could not implement them. I would like to learn more about it, and so, be able to use these techniques in the current and future projects development.

