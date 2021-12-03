import socket

mi_socket = socket.socket()
mi_socket.bind(('localhost', 8000))
mi_socket.listen(5)

while True:
    conexion, address = mi_socket.accept()
    print("Nueva conexion establecida!")
    print(address)

    peticion = conexion.recv(1024)
    print(peticion)

    conexion.send("Hola desde el server")
    conexion.close()