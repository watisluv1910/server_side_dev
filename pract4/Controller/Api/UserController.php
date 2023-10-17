<?php
class UserController extends BaseController
{
    /** 
     * "/user/list" Endpoint - Get list of users 
     */
    public function listAction()
    {
        $strErrorDesc = '';
        $requestMethod = $_SERVER["REQUEST_METHOD"];
        $arrQueryStringParams = $this->getQueryStringParams();
        if (strtoupper($requestMethod) == 'GET') {
            try {
                $userModel = new UserModel();
                $intLimit = 10;
                if (isset($arrQueryStringParams['limit']) && $arrQueryStringParams['limit']) {
                    $intLimit = $arrQueryStringParams['limit'];
                }
                $arrUsers = $userModel->getUsers($intLimit);
                $responseData = json_encode($arrUsers);
            } catch (Error $e) {
                $strErrorDesc = $e->getMessage() . 'Something went wrong! Please contact support.';
                $strErrorHeader = 'HTTP/1.1 500 Internal Server Error';
            }
        } else {
            $strErrorDesc = 'Method not supported';
            $strErrorHeader = 'HTTP/1.1 422 Unprocessable Entity';
        }

        if (!$strErrorDesc) {
            $this->sendOutput(
                $responseData,
                array('Content-Type: application/json', 'HTTP/1.1 200 OK')
            );
        } else {
            $this->sendOutput(
                json_encode(array('error' => $strErrorDesc)),
                array('Content-Type: application/json', $strErrorHeader)
            );
        }
    }

    public function createAction()
    {
        $strErrorDesc = '';
        $requestMethod = $_SERVER["REQUEST_METHOD"];

        if (strtoupper($requestMethod) == 'POST') {
            $json = file_get_contents('php://input');
            $data = json_decode($json, true);

            if ($data) {
                try {
                    $userModel = new UserModel();
                    $userModel->insertUser($data);
                    $responseData = json_encode(['message' => 'User created successfully']);
                } catch (Error $e) {
                    $strErrorDesc = $e->getMessage() . 'Something went wrong! Please contact support.';
                    $strErrorHeader = 'HTTP/1.1 500 Internal Server Error';
                }
            } else {
                $strErrorDesc = 'Invalid JSON data';
                $strErrorHeader = 'HTTP/1.1 400 Bad Request';
            }
        } else {
            $strErrorDesc = 'Method not supported';
            $strErrorHeader = 'HTTP/1.1 422 Unprocessable Entity';
        }

        if (!$strErrorDesc) {
            $this->sendOutput(
                $responseData,
                ['Content-Type: application/json', 'HTTP/1.1 201 Created']
            );
        } else {
            $this->sendOutput(
                json_encode(['error' => $strErrorDesc]),
                ['Content-Type: application/json', $strErrorHeader]
            );
        }
    }

    public function updateAction()
    {
        $strErrorDesc = '';
        $requestMethod = $_SERVER["REQUEST_METHOD"];

        if (strtoupper($requestMethod) == 'PUT') {
            $json = file_get_contents('php://input');
            $data = json_decode($json, true);

            if ($data) {
                try {
                    $userModel = new UserModel();
                    $userModel->updateUser($data);
                    $responseData = json_encode(['message' => 'User updated successfully']);
                } catch (Error $e) {
                    $strErrorDesc = $e->getMessage() . 'Something went wrong! Please contact support.';
                    $strErrorHeader = 'HTTP/1.1 500 Internal Server Error';
                }
            } else {
                $strErrorDesc = 'Invalid JSON data';
                $strErrorHeader = 'HTTP/1.1 400 Bad Request';
            }
        } else {
            $strErrorDesc = 'Method not supported';
            $strErrorHeader = 'HTTP/1.1 422 Unprocessable Entity';
        }

        // Send output
        if (!$strErrorDesc) {
            $this->sendOutput(
                $responseData,
                ['Content-Type: application/json', 'HTTP/1.1 200 Resource updated successfully']
            );
        } else {
            $this->sendOutput(
                json_encode(['error' => $strErrorDesc]),
                ['Content-Type: application/json', $strErrorHeader]
            );
        }
    }

    public function deleteAction()
    {
        $strErrorDesc = '';
        $requestMethod = $_SERVER["REQUEST_METHOD"];
        $arrQueryStringParams = $this->getQueryStringParams();

        if (strtoupper($requestMethod) == 'DELETE') {
            if (isset($arrQueryStringParams['id'])) {
                $user_id = intval($arrQueryStringParams['id']);
                if ($user_id > 0) {
                    try {
                        $userModel = new UserModel();
                        $userModel->deleteUser($user_id);
                        $responseData = json_encode(['message' => 'User deleted successfully']);
                    } catch (Error $e) {
                        $strErrorDesc = $e->getMessage() . ' Something went wrong! Please contact support.';
                        $strErrorHeader = 'HTTP/1.1 500 Internal Server Error';
                    }
                } else {
                    $strErrorDesc = 'Invalid user_id';
                    $strErrorHeader = 'HTTP/1.1 400 Bad Request';
                }
            } else {
                $strErrorDesc = 'Missing "id" query parameter';
                $strErrorHeader = 'HTTP/1.1 400 Bad Request';
            }
        } else {
            $strErrorDesc = 'Method not supported';
            $strErrorHeader = 'HTTP/1.1 422 Unprocessable Entity';
        }

        // Send output
        if (!$strErrorDesc) {
            $this->sendOutput(
                $responseData,
                ['Content-Type: application/json', 'HTTP/1.1 200 Resource deleted successfully']
            );
        } else {
            $this->sendOutput(
                json_encode(['error' => $strErrorDesc]),
                ['Content-Type: application/json', $strErrorHeader]
            );
        }
    }
}
